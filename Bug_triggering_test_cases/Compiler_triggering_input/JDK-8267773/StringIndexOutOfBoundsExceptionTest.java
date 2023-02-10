import java.lang.StringIndexOutOfBoundsException;
public class StringIndexOutOfBoundsExceptionTest
{
	int failures = 0;
	
    public void runTest()
    {
        System.out.println ("Test: StringIndexOutOfBoundsException(Integer.MIN_VALUE)");
        for ( int i=0; i<100000; i++ ) {
            run1Test(i);
        }
		System.out.println("Test finished.");
    }

    public static void main(String[] argv)
    {
        System.out.println("Running on:");
        System.out.println("os.arch: " + System.getProperty("os.arch"));
        System.out.println("os.name: " + System.getProperty("os.name"));
        System.out.println("java.runtime.version: " + System.getProperty("java.runtime.version"));
        System.out.println("java.vm.name: " + System.getProperty("java.vm.name"));
        System.out.println("java.vm.version: " + System.getProperty("java.vm.version"));

        StringIndexOutOfBoundsExceptionTest test = new StringIndexOutOfBoundsExceptionTest();
        test.runTest();
    }
    
    public void run1Test(int i)
    {
        StringIndexOutOfBoundsException obj = new StringIndexOutOfBoundsException(Integer.MIN_VALUE);
        if ( obj == null ) {
            System.out.println ("Failed: obj == null");
        }
        else {
            if (! obj.toString().equals("java.lang.StringIndexOutOfBoundsException: String index out of range: -2147483648")) {
                System.out.println ("Failed on invocation " + i + ": obj.toString() = \"" + obj.toString() + "\", not \"java.lang.StringIndexOutOfBoundsException: String index out of range: -2147483648\"");
				failures++;
				if (failures >= 20) {
					System.out.println ("Ending test early at 20 failures.");
					System.exit(1);
				}
            }
        }
    }

}