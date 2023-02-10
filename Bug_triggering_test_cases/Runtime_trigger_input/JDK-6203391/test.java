
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
/**
 * Displays JVM behaviour in the case of bad max_locals values.
 * (see VM Spec The class File Format - Section 4.7.3 - The CodeAttribute)
 *
 * For conciseness, what should conventionally be modelled as 3 separate
 * classes has been combined into one. These aspects are:
 *   A target class (that will be modified) with a single target method
 *   A ClassLoader to create Class objects from modified bytecode
 *   A Class to modify bytecode, and invoke the target method
 *
 * @author Josh Micich
 */
public class MyTestClass extends ClassLoader {
	
	//------------------------
	//  Target Class Aspect
	//------------------------
	/**
	 * The max_locals value of this method will be modified for each test
	 */
	public static void targetMethod() {
		int i=0;
		System.out.println("In targetMethod");
		i++;
	}

	//------------------------
	//  ClassLoader Aspect
	//------------------------
	public MyTestClass(ClassLoader parent) {
		super(parent);
	}
	
	public Class makeClass(String className, byte[] bytecode) {
		
		return defineClass(className, bytecode, 0, bytecode.length);
	}

	//------------------------
	// Main Test Runner Aspect
	//------------------------
	public static void main(String[] args) {
		
		int maxLocals = Integer.parseInt(args[0]);
		
		System.out.println("Running test for max_locals=" + maxLocals);

		Class c = MyTestClass.class;
		InputStream is = c.getResourceAsStream("MyTestClass.class");
		byte[] byteCode = createByteArrayFromStream(is);
		
		setMaxLocals(byteCode, maxLocals);
		
		MyTestClass classLoader = new MyTestClass(c.getClassLoader());
		
		Class cPrime = classLoader.makeClass(c.getName(), byteCode);
		
		Method m;
		try {
			m = cPrime.getDeclaredMethod("targetMethod", null);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
		
		try {
			m.invoke(null, null);
			System.out.println("Method call successul");
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			if(!(e.getTargetException() instanceof StackOverflowError)) {
				throw new RuntimeException(e);
			}
			System.out.println("StackOverflowError");
			// fall through
		}
		System.out.println("Successful Exit");
	}
	/**
	 * Modifies the 'u2 max_locals' for the 'targetMethod()'
	 */
	private static void setMaxLocals(byte[] bb, int maxLocals) {

		int max = bb.length - 13;
		
		int location = -1;
		
		for (int i = 0; i < max; i++) {
			if(isDesiredBytecodeLocation(bb, i)) {
				if(location >=0) {
					throw new RuntimeException("more than one bytecode location found");
				}
				location = i;
			}
		}
		if(location < 0) {
			throw new RuntimeException("bytecode location not found");
		}
		location +=2; // +2 step over 'u2 max_stack'
		
		// make the modification to the bytecode:
		bb[location + 0] = (byte)(maxLocals >>  8);
		bb[location + 1] = (byte)(maxLocals >>  0);
	}
	/**
	 * @return true if i is the index of the desired 'u2 max_stack' bytecode element
	 */
	private static boolean isDesiredBytecodeLocation(byte[] bb, int i) {

		final byte EXPECTED_MAX_STACK = 2;
		final byte EXPECTED_MAX_LOCALS = 1;

		if(bb[i+0] != 0 || bb[i+1] != EXPECTED_MAX_STACK) {
		   return false;
		}
		if(bb[i+2] != 0 || bb[i+3] != EXPECTED_MAX_LOCALS) {
	  		return false;
  		}
		// next 4 bytes should be code length
		int codeLength = intFrom4Bytes(bb, i+4);
		
		// this is where the u2 exception_table_length is found
		// (assuming codeLength is valid)
		int exTabLengthIx = i + 8 + codeLength;
		
		if(codeLength < 0 || exTabLengthIx + 4 > bb.length) {
			return false;
		}
		// looking for method with no exception handlers
		if(bb[exTabLengthIx+0] != 0 || bb[exTabLengthIx+1] != 0) {
			return false;
		}
		return true;
	}
	
	private static int intFrom4Bytes(byte[] byteCode, int offset) {
		return
		  ((((int)byteCode[offset + 0]) << 24) & 0xFF000000)
		+ ((((int)byteCode[offset + 1]) << 16) & 0x00FF0000)
		+ ((((int)byteCode[offset + 2]) <<  8) & 0x0000FF00)
		+ ((((int)byteCode[offset + 3]) <<  0) & 0x000000FF);
	}

	private static byte[] createByteArrayFromStream(InputStream is) {

		final int MAX_SIZE = 6000;
		byte[] buf = new byte[MAX_SIZE];

		int totalBytesReadSoFar = 0;
		while (true) {
			int desiredReadLen = buf.length - totalBytesReadSoFar;
			if(desiredReadLen == 0) {
				throw new RuntimeException("buffer is not big enough");
			}
			int bytesRead;
			try {
				bytesRead = is.read(buf, totalBytesReadSoFar, desiredReadLen);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			if (bytesRead < 1) {
				// exhausted input stream
				break;
			}
			totalBytesReadSoFar += bytesRead;
		}

		try {
			is.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		byte[] result = new byte[totalBytesReadSoFar];
		System.arraycopy(buf, 0, result, 0, totalBytesReadSoFar);
		return result;
	}
}
