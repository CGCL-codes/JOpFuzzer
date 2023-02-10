
import java.util.Hashtable;


public class Example
{
    static public void main(String[] args)
    {
        Client c = new Client();
    }
}

abstract class Base
{
    static Hashtable h = new Hashtable();

    static
    {
        h.put (A.instance.toString(), A.instance);
    }
}

class A extends Base
{
    public static A instance = new A();
}

class Client
{
    String s;

    Client()
    {
        // Uncomment to workaround exception
        // BaseClass.java.class.toString();

        s = A.instance.toString();
    }
}

