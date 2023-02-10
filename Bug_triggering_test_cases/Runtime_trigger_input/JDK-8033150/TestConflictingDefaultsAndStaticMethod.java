import static jdk.internal.org.objectweb.asm.Opcodes.*;

import jdk.internal.org.objectweb.asm.*;

/*
PACKAGES: [p2, p2, p1, p1]
689473: p1.T4 => DIRECT STATIC (0, 0)
	PUBLIC:p2.I1:   PUBLIC(CONCRETE,EXTENSIBLE,INSTANCE)
	PUBLIC:p2.I2:   PUBLIC(CONCRETE,EXTENSIBLE,INSTANCE)
	PUBLIC:p1.T3:   PUBLIC(CONCRETE,EXTENSIBLE,STATIC)
	PUBLIC:p1.T4:     null(CONCRETE,EXTENSIBLE,INSTANCE):

ERROR: Result=Exception{exc=class java.lang.IncompatibleClassChangeError}/Result=Single{id=1}
*/

interface I1 { default public void m() {} }
interface I2 { default public void m() {} }
class T3 implements I1, I2 {
    public static void m() {}
}


public class TestConflictingDefaultsAndStaticMethod {
    static final String classI1 = "p1.I1";
    static final String classI2 = "p1.I2";
    static final String classT3 = "p1.T3";
    static final String classT4 = "p1.T4";

    static final String classI1Name = classI1.replaceAll("\\.", "/");
    static final String classI2Name = classI2.replaceAll("\\.", "/");
    static final String classT3Name = classT3.replaceAll("\\.", "/");
    static final String classT4Name = classT4.replaceAll("\\.", "/");


    public static void main(String[] args) throws Exception {
        ClassLoader cl = new ClassLoader() {
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                if (findLoadedClass(name) != null) {
                    return findLoadedClass(name);
                }

                if (classI1.equals(name)) {
                    byte[] classFile = dumpI1();
                    return defineClass(classI1, classFile, 0, classFile.length);
                }
                if (classI2.equals(name)) {
                    byte[] classFile = dumpI2();
                    return defineClass(classI2, classFile, 0, classFile.length);
                }
                if (classT3.equals(name)) {
                    byte[] classFile = dumpT3();
                    return defineClass(classT3, classFile, 0, classFile.length);
                }
                if (classT4.equals(name)) {
                    byte[] classFile = dumpT4();
                    return defineClass(classT4, classFile, 0, classFile.length);
                }
                return super.loadClass(name);
            }
        };

        cl.loadClass(classI1);
        cl.loadClass(classI2);
        cl.loadClass(classT3);
        cl.loadClass(classT4);

//        cl.loadClass(classT4).newInstance();

        int result = (Integer)cl.loadClass(classT4).getDeclaredMethod("test").invoke(null);
        System.out.println(""+result);
    }

    public static byte[] dumpI1() {
        ClassWriter cw = new ClassWriter(0);
        MethodVisitor mv;

        cw.visit(52, ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE, classI1Name, null, "java/lang/Object", null);

        {
            mv = cw.visitMethod(ACC_PUBLIC, "m", "()I", null, null);
            mv.visitCode();
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn(classI1+".m()");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "print", "(Ljava/lang/String;)V");
            mv.visitIntInsn(BIPUSH, 3);
            mv.visitInsn(IRETURN);
            mv.visitMaxs(2, 1);
            mv.visitEnd();
        }
        cw.visitEnd();

        return cw.toByteArray();
    }

    public static byte[] dumpI2() {
        ClassWriter cw = new ClassWriter(0);
        MethodVisitor mv;

        cw.visit(52, ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE, classI2Name, null, "java/lang/Object", null);

        {
            mv = cw.visitMethod(ACC_PUBLIC, "m", "()I", null, null);
            mv.visitCode();
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn(classI2+".m()");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "print", "(Ljava/lang/String;)V");
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

        cw.visit(52, ACC_PUBLIC + ACC_SUPER, classT3Name, null, "java/lang/Object",
                new String[] { classI1Name, classI2Name });

        {
            mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
            mv.visitInsn(RETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }

        {
            mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "m", "()I", null, null);
            mv.visitCode();
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn(classT3+".m()");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "print", "(Ljava/lang/String;)V");
            mv.visitIntInsn(BIPUSH, 1);
            mv.visitInsn(IRETURN);
            mv.visitMaxs(2, 1);
            mv.visitEnd();
        }
        cw.visitEnd();

        cw.visitEnd();

        return cw.toByteArray();
    }

    public static byte[] dumpT4() {
        ClassWriter cw = new ClassWriter(0);
        MethodVisitor mv;

        cw.visit(52, ACC_PUBLIC + ACC_SUPER, classT4Name, null, classT3Name, null);

        {
            mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, "p1/T3", "<init>", "()V");
            mv.visitInsn(RETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "test", "()I", null, null);
            mv.visitCode();
            mv.visitTypeInsn(NEW, classT4Name);
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, classT4Name, "<init>", "()V");
            mv.visitMethodInsn(INVOKESTATIC, classT4Name, "m", "()I");
            mv.visitInsn(IRETURN);
            mv.visitMaxs(3, 2);
            mv.visitEnd();
        }

        cw.visitEnd();

        return cw.toByteArray();
    }
}
