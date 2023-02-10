import jdk.internal.org.objectweb.asm.*;

import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;

import static jdk.internal.org.objectweb.asm.Opcodes.*;

public class IntfMethod {
    static byte[] dumpC() {
        ClassWriter cw = new ClassWriter(0);
        cw.visit(52, ACC_PUBLIC | ACC_SUPER, "C", null, "java/lang/Object", new String[]{"I"});
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "testSpecialIntf", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, "I", "f1", "()V", /*itf=*/false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "testStaticIntf", "()V", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "I", "f2", "()V", /*itf=*/false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "testSpecialClass", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, "C", "f1", "()V", /*itf=*/true);
            mv.visitInsn(RETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }

        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "f2", "()V", null, null);
            mv.visitCode();
            mv.visitInsn(RETURN);
            mv.visitMaxs(0, 1);
            mv.visitEnd();
        }
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "testStaticClass", "()V", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "C", "f2", "()V", /*itf=*/true);
            mv.visitInsn(RETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        cw.visitEnd();
        return cw.toByteArray();
    }

    static byte[] dumpI() {
        ClassWriter cw = new ClassWriter(0);
        cw.visit(52, ACC_PUBLIC | ACC_ABSTRACT | ACC_INTERFACE, "I", null, "java/lang/Object", null);
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "f1", "()V", null, null);
            mv.visitCode();
            mv.visitInsn(RETURN);
            mv.visitMaxs(0, 1);
            mv.visitEnd();
        }
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "f2", "()V", null, null);
            mv.visitCode();
            mv.visitInsn(RETURN);
            mv.visitMaxs(0, 1);
            mv.visitEnd();
        }
        cw.visitEnd();
        return cw.toByteArray();
    }

    static class CL extends ClassLoader {
        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            byte[] classFile;
            switch (name) {
                case "I": classFile = dumpI(); break;
                case "C": classFile = dumpC(); break;
                default:
                    throw new ClassNotFoundException(name);
            }
            return defineClass(name, classFile, 0, classFile.length);
        }
    }

    public static void main(String[] args) throws Throwable {
        Class<?> cls = (new CL()).loadClass("C");
        try (FileOutputStream fos = new FileOutputStream("I.class")) { fos.write(dumpI()); }
        try (FileOutputStream fos = new FileOutputStream("C.class")) { fos.write(dumpC()); }

        for (String name : new String[] { "testSpecialIntf", "testStaticIntf", "testSpecialClass", "testStaticClass"}) {
            System.out.printf("%s: ", name);
            try {
                cls.getMethod(name).invoke(cls.newInstance());
                System.out.println("FAILED (no exception)"); // ICCE should be thrown
            } catch (Throwable e) {
                if (e instanceof InvocationTargetException &&
                    e.getCause() != null && e.getCause() instanceof IncompatibleClassChangeError) {
                    System.out.println("PASSED");
                    continue;
                }
                System.out.println("FAILED (unexpected exception is thrown)"); // ICCE should be thrown
            }

        }
    }
}
