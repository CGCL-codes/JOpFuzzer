
//
// !!! The ASM 5.x (http://asm.ow2.org/) library MUST be available in class path to compile and run the following code!
//

import java.util.*;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.objectweb.asm.*;

public class HelloWorldGen implements Opcodes {

    public static String GENERATED_CLASS_NAME = "BadHelloWorld";

	public static byte[] dump () throws Exception {

		ClassWriter cw = new ClassWriter(0);
		FieldVisitor fv;
		MethodVisitor mv;
		AnnotationVisitor av0;

		cw.visit(52, ACC_PUBLIC + ACC_SUPER, GENERATED_CLASS_NAME, null, "java/lang/Object", null);

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
			mv = cw.visitMethod(ACC_PUBLIC, "toString", "()Ljava/lang/String;", null, null);
			mv.visitCode();
			mv.visitLdcInsn("Hello, world!");
			mv.visitInsn(ARETURN);
			mv.visitMaxs(1, 1);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC + ACC_VARARGS, "main", "([Ljava/lang/String;)V", null, null);
			mv.visitCode();
			mv.visitTypeInsn(NEW, "L" + GENERATED_CLASS_NAME + ";");
			mv.visitInsn(DUP);
			mv.visitMethodInsn(INVOKESPECIAL, "L" + GENERATED_CLASS_NAME + ";", "<init>", "()V", false);
			mv.visitVarInsn(ASTORE, 1);
			mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/Object;)V", false);
			mv.visitInsn(RETURN);
			mv.visitMaxs(2, 2);
			mv.visitEnd();
		}
		cw.visitEnd();

		return cw.toByteArray();
	}

    public static void main(String... args) throws Exception {
        try (OutputStream out = Files.newOutputStream(Paths.get(GENERATED_CLASS_NAME + ".class"));) {
            out.write(dump());
        }
    }

}

