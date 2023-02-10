



public class Test {

    public static void main(String[] args) {

        try {
            Class cl = Class.forName("testInitFlags_2C");
            cl.newInstance();
            System.out.println("FAILED: class loaded and instantiated");
        } catch (ClassFormatError e) {
            System.out.println("caught ClassFormatError (expected)");
        } catch (Throwable e) {
            System.out.println("caught " + e);
        }
    }
}

