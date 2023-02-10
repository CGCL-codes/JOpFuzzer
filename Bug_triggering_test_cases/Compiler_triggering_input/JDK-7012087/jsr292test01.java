import java.dyn.MethodHandle;
import java.dyn.MethodHandles;
import java.dyn.MethodType;

public class jsr292test01 {

    public long method(String a, Integer b, float c) {
        return 0;
    }

    public static void main(String[] args) {
        try {
            MethodHandle mh = MethodHandles.lookup().findVirtual(jsr292test01.class, "method",
                    MethodType.methodType(long.class, String.class, Integer.class, float.class));

            jsr292test01 me = new jsr292test01();
            long l;

            System.err.println("1");
            l = (long) mh.bindTo(me).invokeExact("a", new Integer(1), 3F); // OK

            System.err.println("2");
            l = (long) mh.invokeExact("a", new Integer(1), 3F); // Exception

        } catch ( Throwable t ) {
            t.printStackTrace();
        }
    }

}