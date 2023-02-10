import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;

import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.objectweb.asm.Opcodes.ACC_FINAL;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_STATIC;
import static org.objectweb.asm.Opcodes.ACC_SUPER;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ARETURN;
import static org.objectweb.asm.Opcodes.H_INVOKESTATIC;
import static org.objectweb.asm.Opcodes.V1_8;

/**
 * This mini-application reproduces (when planets align just right) the spurious
 * {@code IncompatibleClassChangeError} thrown at a call site linked to a
 * virtual method defined as a default method of an interface.
 *
 * <p>Failing in JDK 10 and 9; not failing in 1.8.
 */
public class FailureTrigger {

    interface InterfaceWithDefaultMethod {
        /**
         * NOTE: the failure is much more likely to trigger when the called method
         * does a lot of busy work with allocations. A simple {@code return x;}
         * doesn't fail.
         */
        default Object foo(Object x) {
            List<Object> stuff = new ArrayList<>();
            for (int i = 0; i < 3000; i++) {
                stuff.add(new Object());
            }
            System.out.println(x);
            return stuff;
        }
    }

    static class DefaultMethodInheritor implements InterfaceWithDefaultMethod {
    }

    public static void main(String[] args) {
        byte[] bytecode = generateClass();
        Loader loader = new Loader("Test", bytecode);
        try {
            Class<?> testClass = loader.loadClass("Test");
            Method testMethod = testClass.getMethod("test", Object.class, Object.class);
            System.out.println("Invoking test method...");
            DefaultMethodInheritor subject = new DefaultMethodInheritor();
            for (int i = 0; i < 100_000; i++) {
                testMethod.invoke(null, subject, "invocation " + i);
            }
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generate a class with a test method performing an {@code invokedynamic}
     * bootstrapped by the bootstrap method below. The class is essentially this:
     *
     * <pre>{@code
     * public final class Test {
     * public static Object test(Object x, Object y) {
     * return <invokedynamic>(x, y);
     * }
     * }
     * }</pre>
     */
    private static byte[] generateClass() {
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        classWriter.visit(
                V1_8, // can be V9 or V10 in corresponding JDKs, fails just the same
                ACC_PUBLIC | ACC_FINAL | ACC_SUPER,
                "Test",
                null,
                "java/lang/Object",
                null);
        MethodVisitor methodWriter = classWriter.visitMethod(
                ACC_PUBLIC | ACC_STATIC,
                "test",
                "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;",
                null,
                null);
        methodWriter.visitCode();
        methodWriter.visitVarInsn(ALOAD, 0);
        methodWriter.visitVarInsn(ALOAD, 1);
        methodWriter.visitInvokeDynamicInsn("invokeTest", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", BOOTSTRAP);
        methodWriter.visitInsn(ARETURN);
        methodWriter.visitMaxs(-1, -1);
        methodWriter.visitEnd();
        classWriter.visitEnd();
        return classWriter.toByteArray();
    }

    /**
     * The bootstrapper of the {@code invokedynamic} instruction in the test method.
     * The site is permanently linked to {@link InterfaceWithDefaultMethod#foo(Object)},
     * obtained by a lookup in {@link DefaultMethodInheritor}.
     */
    public static CallSite bootstrap(MethodHandles.Lookup lookup, String methodName, MethodType callSiteType) {
        try {
            MethodHandle handler = MethodHandles.lookup()
                    .findVirtual(DefaultMethodInheritor.class, "foo", MethodType.methodType(Object.class, Object.class));
            return new ConstantCallSite(handler.asType(callSiteType));
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new AssertionError(e);
        }
    }

    private static final Handle BOOTSTRAP = new Handle(
            H_INVOKESTATIC,
            "com/foo/bar/FailureTrigger",
            "bootstrap",
            MethodType.methodType(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class)
                    .toMethodDescriptorString(),
            false);


    private static class Loader extends ClassLoader {
        private final String name;
        private final byte[] bytecode;
        private Class<?> loadedClass;

        private Loader(String name, byte[] bytecode) {
            this.name = name;
            this.bytecode = bytecode;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            if (this.name.equals(name)) {
                if (loadedClass == null) {
                    loadedClass = defineClass(name, bytecode, 0, bytecode.length);
                }
                return loadedClass;
            }
            return super.findClass(name);
        }
    }
}