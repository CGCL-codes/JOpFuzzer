
public class TestGCException {
public static void main(String[] args) {
	TestGCOnly();
}
final static Runtime rt=Runtime.getRuntime();
static String runFinalize() {  // return value intended to update e.g. in a Expression window
	rt.runFinalization();
	rt.gc();
	return "Finalized at " +
	(new java.util.Date (System.currentTimeMillis()));
	}
 
public static void TestGCOnly() {
	try {
	System.out.print("Press <Enter> after Attach to process javaw.exe :");
	System.in.read();
	} catch(java.io.IOException iox){};
		
	System.out.println("Starting Test");
	try {
		int loops = 5;
		while (loops-- > 0) {
			for (int i = 0; i < 100; i++) {
				{
				Long l = new Long(123456789);
				Long l2 = -l;
				l = l2 * 2;  // just to avoid "never read" warnings in Eclipse
				}
				Thread.sleep(3);
				runFinalize();
			}
			System.out.print(".");
		}
	} catch (InterruptedException e) {
		System.out.print("!");
	}
	System.out.println(" Done");
}
}
