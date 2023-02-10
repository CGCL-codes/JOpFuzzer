

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import javassist.ClassPool;
import javassist.CtClass;

public class TestAgent implements ClassFileTransformer {
    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new TestAgent());
        
        new DummyClass(); //reference the dummy class, so it goes through the transform
    }

    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classBytes)
        throws IllegalClassFormatException {
        if (className == null || !className.contains("DummyClass")) { //only try to load the DummyClass is sufficient to trigger the error
            return null;
        }
        
        
        CtClass cc = null;
        try {
            cc = ClassPool.getDefault().makeClass(className);
            System.out.println("Javassist loaded [" + cc.getName() + "]");
        } finally {
            if (cc != null) {
                cc.detach();
            }
        }
        return null;
    }
    
    static class DummyClass{}
}


