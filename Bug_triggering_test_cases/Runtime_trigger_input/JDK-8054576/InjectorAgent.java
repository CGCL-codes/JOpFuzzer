

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;  

import javassist.bytecode.analysis.Util;

public class InjectorAgent {

    public static void logMethodInvocation() {
        System.out.println("Logging invocation...");
        //Thread.dumpStack();
        System.out.println("logging complete!");
    }
    public static byte[] getBytesFromIS(InputStream stream) {

    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    try {
        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = stream.read(data, 0, data.length)) != -1) {
        buffer.write(data, 0, nRead);
        }

        buffer.flush();
    } catch (Exception e) {
        System.err.println("Failed to convert IS to byte[]!");
        e.printStackTrace();
    }

    return buffer.toByteArray();

    }


    public static byte[] getBytesFromClass(Class<?> clazz) {            
    return getBytesFromIS(clazz.getClassLoader().getResourceAsStream( clazz.getName().replace('.', '/') + ".class"));   
    }    
    public static void agentmain(String agentArgs, Instrumentation instrumentation) 
	{
    	/*
		try {  
			System.out.println("Running class transformer\n");  
			instrumentation.addTransformer(new SimpleTransformer());        
		} catch (Exception e) {  
			System.out.println("Exception: " + e);  
		} 
		*/ 
		try {
			System.out.println("Now trying again to redefine class");
			instrumentation.retransformClasses(instrumentation.getAllLoadedClasses());
			} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to redefine class!");
			}		
	}       
}

