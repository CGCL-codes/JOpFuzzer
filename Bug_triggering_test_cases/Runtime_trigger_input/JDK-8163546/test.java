
import java.io.IOException;

public class MemoryTest {

	public static void main(String[] args) {
		MemoryTest test = new MemoryTest(); 
		test.doMemTest();
	}

	private  void doMemTest() {
		final int size = 10000000 * 16;

		long startTime = System.currentTimeMillis();
		String[] table = null;
		boolean arrayAllocated = false;
		try {
			table = new String[size];
			arrayAllocated  = true;
		} finally {
			if (arrayAllocated) {
				long endTime = System.currentTimeMillis();
				System.err.println("Diff time : " + (endTime - startTime));
			}
		}
		
		arrayAllocated = false;
		int i = 0;
		try {
			for (i = 0; i < size; ++i) {
				table[i] = new String("This String " + i);
				if (i == 2580000) {
					try {
						System.err.println("Pls. press <ENTER> :");
						System.in.read();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if ( ((i+1) % 10000) == 0) {
					System.err.println("# of Strings allocated : " + (i+1));
				}
			}
			arrayAllocated = true;
		} finally {
			long endTime = System.currentTimeMillis();
			System.err.println("Diff time : " + (endTime - startTime));
		}
	}
}

