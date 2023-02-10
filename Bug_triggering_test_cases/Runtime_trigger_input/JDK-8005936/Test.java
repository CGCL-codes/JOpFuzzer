import java.util.*;
public class Test {
	public static List collection = new LinkedList();
	public static void main(String args[]) {
		long executionTime = args.length > 0 ? Integer.valueOf(args[0])*1000 : 60000;
		long startTime = System.currentTimeMillis();
		while(System.currentTimeMillis() - startTime < executionTime) {
			try {
				collection.add(new byte[100]);
			} catch (OutOfMemoryError e) {
				collection = null;
				System.gc();
				collection = new LinkedList();
			}
			if(collection.size() > 100) {
				collection.clear();
			}
		}
	}
}
