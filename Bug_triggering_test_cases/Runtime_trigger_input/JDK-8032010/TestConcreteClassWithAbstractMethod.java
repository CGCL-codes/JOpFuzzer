package test;

import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.MethodVisitor;

import static jdk.internal.org.objectweb.asm.Opcodes.*;

/*
0066: p1.T3 => DIRECT VIRTUAL (1, 0)
        PUBLIC:p1.T1:   PUBLIC(CONCRETE,EXTENSIBLE,INSTANCE);
        PUBLIC:p1.T2:   PUBLIC(ABSTRACT,EXTENSIBLE,INSTANCE);
        PUBLIC:p1.T3:   PUBLIC(CONCRETE,EXTENSIBLE,INSTANCE):

ERROR: Result=Exception{exc=class java.lang.AbstractMethodError}/Result=Single{id=0}
*/
public class TestConcreteClassWithAbstractMethod {
    static final String classT1 = "p1.T1";
    static final String classT2 = "p1.T2";
    static final String classT3 = "p1.T3";

    static final String callerName = classT3;

    public static void main(String[] args) throws Exception {
        ClassLoader cl = new ClassLoader() {
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                if (findLoadedClass(name) != null) {
                    return findLoadedClass(name);
                }

                if (classT1.equals(name)) {
                    byte[] classFile = dumpT1();
                    return defineClass(classT1, classFile, 0, classFile.length);
                }
                if (classT2.equals(name)) {
                    byte[] classFile = dumpT2();
                    return defineClass(classT2, classFile, 0, classFile.length);
                }
                if (classT3.equals(name)) {
                    byte[] classFile = dumpT3();
                    return defineClass(classT3, classFile, 0, classFile.length);
                }

                return super.loadClass(name);
            }
        };

        cl.loadClass(classT1);
        cl.loadClass(classT2);
        cl.loadClass(classT3);

        //cl.loadClass(callerName).getDeclaredMethod("m");
        cl.loadClass(callerName).newInstance();

        int result = (Integer)cl.loadClass(callerName).getDeclaredMethod("test").invoke(null);
        System.out.println(""+result);
    }

    public static byte[] dumpT1() {
        ClassWriter cw = new ClassWriter(0);
        MethodVisitor mv;

        cw.visit(52, ACC_PUBLIC | ACC_SUPER, "p1/T1", null, "java/lang/Object", null);
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
            mv = cw.visitMethod(ACC_PUBLIC, "m", "()I", null, null);
            mv.visitCode();
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("p1/T1.m()");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "print", "(Ljava/lang/String;)V", false);
            mv.visitIntInsn(BIPUSH, 2);
            mv.visitInsn(IRETURN);
            mv.visitMaxs(2, 1);
            mv.visitEnd();
        }
        cw.visitEnd();

        return cw.toByteArray();
    }

    public static byte[] dumpT2() {
        ClassWriter cw = new ClassWriter(0);
        MethodVisitor mv;

        cw.visit(52, ACC_PUBLIC | ACC_SUPER, "p1/T2", null, "p1/T1", null);
        {
            mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, "p1/T1", "<init>", "()V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "m", "()I", null, null);
            mv.visitEnd();
        }
        cw.visitEnd();

        return cw.toByteArray();
    }

    public static byte[] dumpT3() {
        ClassWriter cw = new ClassWriter(0);
        MethodVisitor mv;

        cw.visit(52, ACC_PUBLIC + ACC_SUPER, "p1/T3", null, "p1/T2", null);

        {
            mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, "p1/T2", "<init>", "()V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "m", "()I", null, null);
            mv.visitCode();
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("p1/T3.m()");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "print", "(Ljava/lang/String;)V", false);
            mv.visitIntInsn(BIPUSH, 2);
            mv.visitInsn(IRETURN);
            mv.visitMaxs(2, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "test", "()I", null, null);
            mv.visitCode();
            mv.visitTypeInsn(NEW, "p1/T3");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "p1/T3", "<init>", "()V", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "p1/T2", "m", "()I", false);
            mv.visitInsn(IRETURN);
            mv.visitMaxs(3, 2);
            mv.visitEnd();
        }
        cw.visitEnd();

        return cw.toByteArray();
    }
}