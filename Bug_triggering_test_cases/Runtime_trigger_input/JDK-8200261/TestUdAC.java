import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import sun.misc.Unsafe;

import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.objectweb.asm.Opcodes.ACC_PRIVATE;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_SUPER;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ARETURN;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.GETFIELD;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.PUTFIELD;
import static org.objectweb.asm.Opcodes.RETURN;
import static org.objectweb.asm.Opcodes.V1_8;

public class TestUdAC {

    static final Unsafe UNSAFE;

    static {
        try {
            Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            UNSAFE = (Unsafe) unsafeField.get(null);
        }
        catch (Exception e) {
            throw new InternalError(e);
        }
    }

    interface Resource {
        Pointer ptr();
    }

    interface Struct extends Resource {
       StructPointer ptr(); //commenting this line (or removing covariant override) makes everything ok
    }

    interface Pointer { }
    
    interface StructPointer extends Pointer { }

    interface I extends Struct {
        void m(); //commenting this makes everything ok
    }

    static String IMPL_PREFIX = "$$impl";
    static String PTR_FIELD_NAME = "ptr";

    public static void main(String[] args) throws Throwable {
        byte[] bytes = new TestUdAC().generate(I.class);
        Class<?> cl = UNSAFE.defineAnonymousClass(I.class, bytes, new Object[0]);
        I i = (I)cl.getConstructors()[0].newInstance(new Object[] { null }); //exception here!
    }

    byte[] generate(Class<?> iface) {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);

        String ifaceTypeName = Type.getInternalName(iface);
        String proxyClassName = ifaceTypeName + IMPL_PREFIX;

        // class definition
        cw.visit(V1_8, ACC_PUBLIC + ACC_SUPER, proxyClassName,
                desc(Object.class) + desc(ifaceTypeName) + desc(Struct.class),
                name(Object.class),
                new String[] { ifaceTypeName, name(Struct.class) });

        cw.visitField(ACC_PRIVATE, PTR_FIELD_NAME, desc(StructPointer.class), desc(StructPointer.class), null);
        cw.visitEnd();

        // constructor
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>",
                meth(desc(void.class), desc(StructPointer.class)),
                meth(desc(void.class), desc(StructPointer.class)), null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, name(Object.class), "<init>", meth(desc(void.class)), false);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitFieldInsn(PUTFIELD, proxyClassName, PTR_FIELD_NAME, desc(StructPointer.class));
        mv.visitInsn(RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();

        // ptr() impl
        mv = cw.visitMethod(ACC_PUBLIC, PTR_FIELD_NAME, meth(desc(StructPointer.class)),
                meth(desc(StructPointer.class)), null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD, proxyClassName, PTR_FIELD_NAME, desc(StructPointer.class));
        mv.visitInsn(ARETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();
        return cw.toByteArray();
    }

    String name(Class<?> clazz) {
            if (clazz.isPrimitive()) {
                throw new IllegalStateException();
            } else if (clazz.isArray()) {
                return desc(clazz);
            } else {
                return clazz.getName().replaceAll("\\.", "/");
            }
        }

        String desc(Class<?> clazz) {
            String mdesc = MethodType.methodType(clazz).toMethodDescriptorString();
            return mdesc.substring(mdesc.indexOf(')') + 1);
        }

        String desc(String clazzName) {
            return "L" + clazzName + ";";
        }

        String gen(String clazz, String... typeargs) {
            return clazz.substring(0, clazz.length() - 1) + Stream.of(typeargs).collect(Collectors.joining("", "<", ">")) + ";";
        }

        String meth(String restype, String... argtypes) {
            return Stream.of(argtypes).collect(Collectors.joining("", "(", ")")) + restype;
        }

        String meth(Method m) {
            return MethodType.methodType(m.getReturnType(), m.getParameterTypes()).toMethodDescriptorString();
        }
}
