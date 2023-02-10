import java.lang.reflect.*;
import sun.misc.*;

public class UnsafeGetAndAddLongTest {

	private long ctl;

	private static final sun.misc.Unsafe U;
	private static final long CTL;

	static {
        	try {
            		Field unsafe = Unsafe.class.getDeclaredField("theUnsafe");
			unsafe.setAccessible(true);
			U = (Unsafe) unsafe.get(null);
			CTL = U.objectFieldOffset(UnsafeGetAndAddLongTest.class.getDeclaredField("ctl"));
		} catch (Exception e) {
			throw new Error(e);
		}
	}

	public static void main(String[] args) {
		for(int c = 0; c < 10000000; c++) {
			new UnsafeGetAndAddLongTest().makeTest();
		}
	}

	// FAILS:
	public static final long EXPECTED = 1L << 42;

	// WORKS:
//	public static final long EXPECTED = 1L << 16;

	// ALSO WORKS:
//	public volatile long EXPECTED = 1L << 42;

	public void makeTest() {
             U.getAndAddLong(this, CTL, EXPECTED);
	     if (ctl != EXPECTED) {
		throw new IllegalStateException("Expected: " + EXPECTED + ", but got = " + ctl);
	     }
	}
}
