import java.lang.reflect.*;

public class ArraySet {

    public static void main(String argv[]) throws Throwable {
        try {
            Integer array[] = { new Integer(1), new Integer(2), new Integer(3) };
            Array.set( array, 2, new Byte((byte) 5) );
        } catch (Exception e) {
            System.out.println("e: " + e.toString());
        }
    }
}