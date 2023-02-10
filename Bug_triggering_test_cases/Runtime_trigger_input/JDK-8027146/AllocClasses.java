package alloc;
import java.util.*;
import java.io.*;

class EmptyClass  { }

public class AllocClasses extends ClassLoader {
	public static void main(String args[]) throws Exception {
    if (Boolean.getBoolean("pcl")) registerAsParallelCapable();
    long start = System.currentTimeMillis();
		while (start - System.currentTimeMillis() < 120000) {
			AllocClasses loader = new AllocClasses();
			loader.runme();
			loader = null;
		}
	}

	private void runme() throws Exception {
		List<Class> classes = new LinkedList<Class>();
		long endTime = System.currentTimeMillis() + 60000;
		int lastClass = 0;
		getBytecode();
		int oomcount = 0;
		while(oomcount < 2) {
			try {
				while(true) {
					String name = "C"+lastClass;
					lastClass++;
					while(name.length() < patchLength) {
						name+="c";
					}
					Class klass = defineClass(name, patchBytecode(name), 0, bytecode.length);
					classes.add(klass);

          if (classes.size() > 1000) return;
				}	
			} catch (OutOfMemoryError oome) {
				System.err.println("Caught oom: " + ++oomcount);
			}
		}
	}

	private int patchPosition = 0;
	private int patchLength = 0;
	private byte bytecode[] = null;

	private void getBytecode() throws Exception {
		String name = EmptyClass.class.getName().replace('.', '/') + ".class";
		InputStream inputStream = EmptyClass.class.getClassLoader().getResourceAsStream(name);
		byte nameAsBytes[] = EmptyClass.class.getName().replace('.', '/').getBytes("UTF8");
		boolean matched;

		// read class file
		bytecode = new byte[inputStream.available()];
		inputStream.read(bytecode);
		inputStream.close();
		
		// find class name position and length
		patchLength = nameAsBytes.length;
		for(patchPosition = 0;
				patchPosition < bytecode.length - patchLength;
				patchPosition++) {
			matched = true;
			for(int i = 0; i < patchLength && matched; i++) {
				matched = bytecode[patchPosition+i] == nameAsBytes[i];
			}
			if(matched) {
				break;
			}
		}
	}

	private byte[] patchBytecode(String name) throws Exception {
		byte newClass[] = Arrays.copyOf(bytecode, bytecode.length);
		byte nameAsBytes[] = name.getBytes("UTF8");
    System.arraycopy(nameAsBytes, 0, newClass, patchPosition, patchLength);
		return newClass;
	}

}

