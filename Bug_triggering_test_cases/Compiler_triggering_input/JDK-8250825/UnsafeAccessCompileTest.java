import java.lang.reflect.Field;
import sun.misc.Unsafe;

public class UnsafeAccessCompileTest {

    private static Unsafe initUnsafe() {
        try {
            // Fast path when we are trusted.
            return Unsafe.getUnsafe();
        } catch (SecurityException se) {
            // Slow path when we are not trusted.
            try {
                Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
                theUnsafe.setAccessible(true);
                return (Unsafe) theUnsafe.get(Unsafe.class);
            } catch (Exception e) {
                throw new RuntimeException("exception while trying to get Unsafe", e);
            }
        }
    }

    private static final Unsafe UNSAFE = initUnsafe();
    private static short onHeapMemory = (short)0x0102;
    private static final Object onHeapMemoryBase;
    private static final long onHeapMemoryOffset;

    static {
        try {
            Field staticField = UnsafeAccessCompileTest.class.getDeclaredField("onHeapMemory");
            onHeapMemoryBase = UNSAFE.staticFieldBase(staticField);
            onHeapMemoryOffset = UNSAFE.staticFieldOffset(staticField);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte test() {
        return UNSAFE.getByte(onHeapMemoryBase, onHeapMemoryOffset + 1);
    }

    public static void main(String[] args) {
        System.out.println(test());
    }
}