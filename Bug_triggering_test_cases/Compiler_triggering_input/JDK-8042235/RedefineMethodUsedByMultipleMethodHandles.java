import java.io.*;
import java.lang.instrument.*;
import java.lang.invoke.*;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.management.*;
import java.lang.reflect.*;
import java.nio.file.*;
import java.security.*;
import java.util.jar.*;

import javax.tools.*;

import jdk.internal.org.objectweb.asm.*;

/**
 * javac -XDignore.symbol.file=true RedefineMethodUsedByMultipleMethodHandles.java
 * java RedefineMethodUsedByMultipleMethodHandles
 */
public class RedefineMethodUsedByMultipleMethodHandles {

    static class Foo {
        public static Object getName() {
            return "foo";
        }
    }

    public static void main(String[] args) throws Throwable {

        Lookup lookup = MethodHandles.lookup();
        Method fooMethod = Foo.class.getDeclaredMethod("getName");

        // fooMH2 displaces fooMH1 from the MemberNamesTable
        MethodHandle fooMH1 = lookup.unreflect(fooMethod);
        MethodHandle fooMH2 = lookup.unreflect(fooMethod);

        System.out.println("fooMH1.invoke = " + fooMH1.invokeExact());
        System.out.println("fooMH2.invoke = " + fooMH2.invokeExact());

        // Redefining Foo.getName() causes vmtarget to be updated
        // in fooMH2 but not fooMH1
        redefineFoo();

        // Full GC causes fooMH1.vmtarget to be deallocated
        System.gc();

        // Calling fooMH1.vmtarget crashes the VM
        System.out.println("fooMH1.invoke = " + fooMH1.invokeExact());
    }

    /**
     * Adds the class file bytes for {@code c} to {@code jar}.
     */
    static void add(JarOutputStream jar, Class<?> c) throws IOException {
        String classAsPath = c.getName().replace('.', '/') + ".class";
        jar.putNextEntry(new JarEntry(classAsPath));
        InputStream stream = c.getClassLoader().getResourceAsStream(classAsPath);

        int b;
        while ((b = stream.read()) != -1) {
            jar.write(b);
        }
    }

    static void redefineFoo() throws Exception {
        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        Attributes mainAttrs = manifest.getMainAttributes();
        mainAttrs.putValue("Agent-Class", FooAgent.class.getName());
        mainAttrs.putValue("Can-Redefine-Classes", "true");
        mainAttrs.putValue("Can-Retransform-Classes", "true");

        Path jar = Files.createTempFile("myagent", ".jar");
        try {
            JarOutputStream jarStream = new JarOutputStream(new FileOutputStream(jar.toFile()), manifest);
            add(jarStream, FooAgent.class);
            add(jarStream, FooTransformer.class);
            jarStream.close();
            runAgent(jar);
        } finally {
            Files.deleteIfExists(jar);
        }
    }

    public static void runAgent(Path agent) throws Exception {
        String vmName = ManagementFactory.getRuntimeMXBean().getName();
        int p = vmName.indexOf('@');
        assert p != -1 : "VM name not in <pid>@<host> format: " + vmName;
        String pid = vmName.substring(0, p);
        ClassLoader cl = ToolProvider.getSystemToolClassLoader();
        Class<?> c = Class.forName("com.sun.tools.attach.VirtualMachine", true, cl);
        Method attach = c.getDeclaredMethod("attach", String.class);
        Method loadAgent = c.getDeclaredMethod("loadAgent", String.class);
        Method detach = c.getDeclaredMethod("detach");
        Object vm = attach.invoke(null, pid);
        loadAgent.invoke(vm, agent.toString());
        detach.invoke(vm);
    }

    public static class FooAgent {

        public static void agentmain(@SuppressWarnings("unused") String args, Instrumentation inst) throws Exception {
            assert inst.isRedefineClassesSupported();
            assert inst.isRetransformClassesSupported();
            inst.addTransformer(new FooTransformer(), true);
            Class<?>[] classes = inst.getAllLoadedClasses();
            for (int i = 0; i < classes.length; i++) {
                Class<?> c = classes[i];
                if (c == Foo.class) {
                    inst.retransformClasses(new Class[]{c});
                }
            }
        }
    }

//    static class FooTransformer implements ClassFileTransformer, Opcodes {
    static class FooTransformer implements ClassFileTransformer {

        @Override
        public byte[] transform(ClassLoader cl, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
            if (Foo.class.equals(classBeingRedefined)) {
                System.out.println("redefining " + classBeingRedefined);
                ClassReader cr = new ClassReader(classfileBuffer);
                ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES);
                ClassVisitor adapter = new ClassVisitor(Opcodes.ASM5, cw) {
                    @Override
                    public MethodVisitor visitMethod(int access, String base, String desc, String signature, String[] exceptions) {
                        MethodVisitor mv = cv.visitMethod(access, base, desc, signature, exceptions);
                        if (mv != null) {
                            mv = new MethodVisitor(Opcodes.ASM5, mv) {
                                @Override
                                public void visitLdcInsn(Object cst) {
                                    System.out.println("replacing \"" + cst + "\" with \"bar\"");
                                    mv.visitLdcInsn("bar");
                                }
                            };
                        }
                        return mv;
                    }
                };

                cr.accept(adapter, ClassReader.SKIP_FRAMES);
                cw.visitEnd();
                return cw.toByteArray();
            }
            return classfileBuffer;
        }
    }
}
