

public class Test {

    public static void main(String[] args) {

        System.out.println("Verify: public abstract synchronized interface method");
        try {
            Class cl = Class.forName("testMethodsIntfFlags_4C");
            cl.newInstance();
            return failed("class loaded and instantiated");
        } catch (ClassFormatError e) {
            System.out.println("caught ClassFormatError (expected)");
        } catch (Throwable e) {
            System.out.println("caught " + e);
        }
    }
}

