import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.ConstantDynamic;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.FileOutputStream;
import java.io.IOException;

public class Generator {

	private static final String CLASS_NAME = "Example";
	private static final String B_NAME = "$jacocoInit";
	private static final String B_DESC = "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;";

	public static void main(String[] args) throws IOException {
		ClassNode c = new ClassNode(Opcodes.ASM7);
		c.version = Opcodes.V11;
		c.access = Opcodes.ACC_PUBLIC;
		c.name = CLASS_NAME;
		c.superName = "java/lang/Object";

		MethodNode m = new MethodNode(Opcodes.ASM7, Opcodes.ACC_STATIC, B_NAME, B_DESC, null, null);
		m.visitMethodInsn(Opcodes.INVOKESTATIC, "StaticInit", "get", "()Ljava/lang/Object;", false);
		m.visitInsn(Opcodes.ARETURN);
		c.methods.add(m);

		m = new MethodNode(Opcodes.ASM7, Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
		Handle bootstrapMethod = new Handle(Opcodes.H_INVOKESTATIC, CLASS_NAME, B_NAME, B_DESC, false);
		m.visitLdcInsn(new ConstantDynamic("$jacocoData", "Ljava/lang/Object;", bootstrapMethod));
		m.visitVarInsn(Opcodes.ASTORE, 1);
		m.visitInsn(Opcodes.RETURN);
		c.methods.add(m);

		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
		c.accept(cw);
		byte[] bytes = cw.toByteArray();

		FileOutputStream fos = new FileOutputStream("Example.class");
		fos.write(bytes);
		fos.close();
	}

}
