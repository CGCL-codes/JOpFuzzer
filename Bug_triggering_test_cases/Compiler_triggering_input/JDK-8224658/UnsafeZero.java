import java.lang.reflect.Field;
import sun.misc.Unsafe;

public class UnsafeZero {
    static final Unsafe U;
    static boolean f;

    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            U = (Unsafe) f.get(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String... args) {
        if (f) { // compile this branch, but don't actually take it during run
            U.getInt(0);
        }
    }
}
