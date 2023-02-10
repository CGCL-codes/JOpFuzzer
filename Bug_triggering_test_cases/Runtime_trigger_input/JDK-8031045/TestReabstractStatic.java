package test;

import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.MethodVisitor;

import static jdk.internal.org.objectweb.asm.Opcodes.*;

/*
30751: p1.T3 => (0, 0)
	PUBLIC:p1.I1:   PUBLIC(CONCRETE,EXTENSIBLE,INSTANCE);
	PUBLIC:p1.I2:   PUBLIC(ABSTRACT,EXTENSIBLE,INSTANCE);
	PUBLIC:p1.T3:   null:
 */
public class TestReabstractStatic {
    static final String classT1 = "p1.I1";
    static final String classT2 = "p1.I2";
    static final String classT3 = "p1.T3";

    static final String staticName = classT1;
    static final String dynamicName = classT3;
    static final String getInstanceName = dynamicName+"Getter";

    static final String callerName = classT3;

    public static void main(String[] args) throws Exception {
        ClassLoader cl = new ClassLoader() {
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                if (findLoadedClass(name) != null) {
                    return findLoadedClass(name);
                }

                if (classT1.equals(name)) {
                    byte[] classFile = dumpI1();
                    return defineClass(classT1, classFile, 0, classFile.length);
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

        cl.loadClass(classT1);
        cl.loadClass(classT2);
        cl.loadClass(classT3);

        //cl.loadClass(callerName).getDeclaredMethod("m");
        cl.loadClass(callerName).newInstance();

        int result = (Integer)cl.loadClass(callerName).getDeclaredMethod("test").invoke(null);
        System.out.println(""+result);
    }

    public static byte[] dumpI1() {
        ClassWriter cw = new ClassWriter(0);
        MethodVisitor mv;

        cw.visit(52, ACC_ABSTRACT | ACC_INTERFACE, "p1/I1", null, "java/lang/Object", null);

        {
            /* NB: if commented, the test throws AME as expected */
            mv = cw.visitMethod(ACC_PUBLIC, "m", "()I", null, null);
            mv.visitCode();
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("p1/I1.m()");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "print", "(Ljava/lang/String;)V", false);
            mv.visitIntInsn(BIPUSH, 2);
            mv.visitInsn(IRETURN);
            mv.visitMaxs(2, 1);
            mv.visitEnd();
            /* */
        }
        cw.visitEnd();

        return cw.toByteArray();
    }

    public static byte[] dumpI2() {
        ClassWriter cw = new ClassWriter(0);
        MethodVisitor mv;

        cw.visit(52, ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE, "p1/I2", null, "java/lang/Object", new String[] { "p1/I1" });

        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "m", "()I", null, null);
/*            mv = cw.visitMethod(ACC_PUBLIC, "m", "()I", null, null);
            mv.visitCode();
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("p1/I1.m()");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "print", "(Ljava/lang/String;)V", false);
            mv.visitIntInsn(BIPUSH, 2);
            mv.visitInsn(IRETURN);
            mv.visitMaxs(2, 1);
            */
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
        /*{
            mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "m", "()I", null, null);
            mv.visitCode();
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("p1/T3.m()");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "print", "(Ljava/lang/String;)V", false);
            mv.visitIntInsn(BIPUSH, 0);
            mv.visitInsn(IRETURN);
            mv.visitMaxs(2, 1);
            mv.visitEnd();
        }*/
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "test", "()I", null, null);
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "p1/T3", "m", "()I", false);
            mv.visitInsn(IRETURN);
            mv.visitMaxs(3, 2);
            mv.visitEnd();
        }
        cw.visitEnd();

        return cw.toByteArray();
    }
}
