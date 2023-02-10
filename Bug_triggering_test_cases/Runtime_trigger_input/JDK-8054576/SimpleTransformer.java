

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class SimpleTransformer implements ClassFileTransformer {

	@Override
	public byte[] transform(ClassLoader loader, String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classfileBuffer) throws IllegalClassFormatException {

		if (!className.contains("amazon")) return classfileBuffer;

		System.out.println("SimpleTransformer invoked on " + className);

		ClassReader reader = new ClassReader(classfileBuffer);
		ClassWriter writer = new ClassWriter(reader, 0);

		ClassVisitor visitor = new ClassVisitor(Opcodes.ASM4, writer) {

			@Override
			public MethodVisitor visitMethod(int access, final String name, String desc, String signature, String[] exceptions) {
				MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
				if (!name.contains("Assess")) return mv;
				else
					System.out.println("Modifying method "+name);

				MethodVisitor mv2 = new MethodVisitor(Opcodes.ASM4, mv) {
					public void visitCode() {
						mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/amazon/inspector/injectoragent/InjectorAgent", "logMethodInvocation", "()V");
					}
				};

				return mv2;
			}
		};


		reader.accept(visitor, 0);
		return writer.toByteArray();    
	}

}    


