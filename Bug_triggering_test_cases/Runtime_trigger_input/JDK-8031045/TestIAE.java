package test;

import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.MethodVisitor;

import static jdk.internal.org.objectweb.asm.Opcodes.*;

public class TestIAE {
    static final String classT2 = "p1.I2";
    static final String classT3 = "p1.T3";

    static final String callerName = classT3;

    public static void main(String[] args) throws Exception {
        ClassLoader cl = new ClassLoader() {
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                if (findLoadedClass(name) != null) {
                    return findLoadedClass(name);
                }
                if (classT2.equals(name)) {
                    byte[] classFile = dumpI2();
                    return defineClass(classT2, classFile, 0, classFile.length);
                }
                if (classT3.equals(name)) {
                    byte[] classFile = dumpT3();
                    return defineClass(classT3, classFile, 0, classFile.length);
                }
                return super.loadClass(name);
            }
        };
        cl.loadClass(classT2);
        cl.loadClass(classT3);
        cl.loadClass(callerName);
        cl.loadClass(callerName).newInstance();

        int result = (Integer)cl.loadClass(callerName).getDeclaredMethod("test").invoke(null);
        System.out.println(""+result);
    }

    public static byte[] dumpI2() {
        ClassWriter cw = new ClassWriter(0);
        MethodVisitor mv;

        cw.visit(52, ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE, "p1/I2", null, "java/lang/Object", null);

        {
            mv = cw.visitMethod(ACC_PRIVATE | ACC_STATIC, "m", "()I", null, null);
            mv.visitCode();
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("p1/I2.m()");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "print", "(Ljava/lang/String;)V", false);
            mv.visitIntInsn(BIPUSH, 2);
            mv.visitInsn(IRETURN);
            mv.visitMaxs(2, 1);
            mv.visitEnd();
        }
        cw.visitEnd();

        return cw.toByteArray();
    }

    public static byte[] dumpT3() {
        ClassWriter cw = new ClassWriter(0);
        MethodVisitor mv;

        cw.visit(52, ACC_PUBLIC + ACC_SUPER, "p1/T3", null, "java/lang/Object", new String[] { "p1/I2" });

        {
            mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "test", "()I", null, null);
            mv.visitCode();
            mv.visitTypeInsn(NEW, "p1/T3");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "p1/T3", "<init>", "()V", false);
            mv.visitMethodInsn(INVOKEINTERFACE, "p1/I2", "m", "()I", true);
            mv.visitInsn(IRETURN);
            mv.visitMaxs(3, 2);
            mv.visitEnd();
        }
        cw.visitEnd();

        return cw.toByteArray();
    }
}
