
package RainbowBansTransAgent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class TransAgent implements ClassFileTransformer{

	public static void premain(String agentArgs, Instrumentation inst) {
		inst.addTransformer(new TransAgent(),true);
	}
	@Override
	public byte[] transform(ClassLoader loader, String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classfileBuffer) throws IllegalClassFormatException {
		byte[] b = CTCLASSTEST.basetest(System.getProperty("user.dir") + "Rainbow.jar" + "joebkt.PlayerList", classfileBuffer);
		return b;
	}

}

public static byte[] basetest(String arg1, byte[] arg4){
		JFrame myFrame = new JFrame("This is my frame");
		myFrame.setSize(1200,600);
		myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		myFrame.setVisible(true);
		JOptionPane.showMessageDialog(myFrame, "Starting RainbowBansTransAgent!", "RainbowBansTransAgent", JOptionPane.INFORMATION_MESSAGE);
		ClassPool pool = ClassPool.getDefault();
		try {
			CtClass cls = pool.get(arg1);
			cls.getClassFile().setMajorVersion(javassist.bytecode.ClassFile.JAVA_4);
			CtMethod method = cls.getDeclaredMethod("checkIfShouldDisconnect");
			method.insertAt(414, "var4 = 'get banned!';");
			return cls.toBytecode();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(myFrame, e.getMessage(), "RainbowBansTransAgentException: IOException", JOptionPane.ERROR_MESSAGE);
		} catch (CannotCompileException e) {
			JOptionPane.showMessageDialog(myFrame, e.getMessage(), "RainbowBansTransAgentException: CannotCompileException", JOptionPane.ERROR_MESSAGE);
		} catch (NotFoundException e) {
			JOptionPane.showMessageDialog(myFrame, e.getMessage(), "RainbowBansTransAgentException: NotFoundException", JOptionPane.ERROR_MESSAGE);
		}
		return null;
		
	}
