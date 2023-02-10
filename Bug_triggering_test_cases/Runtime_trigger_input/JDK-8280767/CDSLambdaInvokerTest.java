import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class CDSLambdaInvokerTest {
    public static void main(String args[]) throws Throwable {
        invoke(MethodHandles.identity(double.class), 1.0);
        invoke(MethodHandles.identity(long.class), 1);
        invoke(MethodHandles.identity(int.class), 1);
        invoke(MethodHandles.identity(float.class), 1.0f);

        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType mt = MethodType.methodType(void.class, float.class, double.class, int.class,
                                              boolean.class, Object.class, long.class, double.class);
        MethodHandle mh = lookup.findStatic(CDSLambdaInvokerTest.class, "callme", mt);
        mh.invokeExact(4.0f, 5.0, 6, true, (Object)args, 7L, 8.0);
    }

    private static Object invoke(MethodHandle mh, Object ... args) throws Throwable {
        try {
            for (Object o : args) {
                mh = MethodHandles.insertArguments(mh, 0, o);
            }
            return mh.invoke();
        } catch (Throwable t) {
            System.out.println("Failed to find, link and/or invoke " + mh.toString() + ": " + t.getMessage());
            throw t;
        }
    }
 
    private static void callme(float f, double d, int i, boolean b, Object o, long l, double d2) {

    }
}
