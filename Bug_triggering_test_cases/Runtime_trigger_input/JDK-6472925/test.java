
import java.util.*;

public class MemErrDiagnose {
	
	private static final Random random = new Random();
	private static long count = 0;
	private static final List<Object> list = new LinkedList<Object>();
	
	public static void main(String[] args) {
		try {
			while (true) {
				allocate();
			}
		}
		catch (Throwable t) {
			System.err.println( t.getClass().getName() );
			t.printStackTrace( System.err );
		}
	}
	
	private static void allocate() {
		System.out.println( "allocate #" + (++count) );
		int n = random.nextInt(1000 * 1000);
		for (int i = 0; i < n; i++) {
			list.add( new Object() );
		}
	}
}
