
class Foo
{
    // Allows the class to compile but fail during loading
    static {
        if (System.getProperty("foo") == null)
            throw new RuntimeException("no system property foo");
    }
}


public class Test
{

    public static void main(String[] args)
            throws Throwable
    {
        try {
            Class.forName("Foo");
        } catch (Throwable t) {
            t.printStackTrace();
        }
        Foo f = new Foo();
    }
}


