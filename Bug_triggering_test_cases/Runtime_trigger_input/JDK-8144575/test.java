
public class EACSmarTest {

    public EACSmarTest() {
        try {

            //System.out.println(System.getProperty("java.library.path"));
            System.loadLibrary("EACSmarTest");
            //System.load("/home/prod/EAP/ShareLib/libEACSmarTest.so");

            System.out.println("library loaded...");
        } catch (UnsatisfiedLinkError e) {
            System.out.println("UnSatisfieldLinkError");
            System.out.println(e);
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        EACSmarTest test = new EACSmarTest();

        test.LookStatus();
    }

    public native void LookStatus();
}
