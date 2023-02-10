import sun.misc.Unsafe;
import java.lang.reflect.Field;
import java.util.*;

public class TestSwapLong {
    private final static Unsafe U;
    private static long result = 4;
    static volatile long[] array = new long[1001];
    static volatile long[] array0 = new long[1001];
    static long t;   
    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            U = (Unsafe) f.get(null);
        } catch (ReflectiveOperationException e) {
            throw new InternalError(e);
        }
    }

    public static void testCompareAndSwapLong() {
        U.compareAndSwapLong(array, Unsafe.ARRAY_LONG_BASE_OFFSET + 1, 3243, 2334);
    }

    public static void main(String[] args) {
        testCompareAndSwapLong();
    }
}
