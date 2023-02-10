import java.lang.reflect.*;
import sun.misc.*;

class unsafecl {
    private static final Unsafe UNSAFE;
    private static final long offset;
    private static volatile Class<?> clazz;

    private static int field;

    static {
        Unsafe u;
        try {
            // Fast path when we are trusted.
            u = Unsafe.getUnsafe();
        } catch (SecurityException se) {
            // Slow path when we are not trusted.
            try {
                Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
                theUnsafe.setAccessible(true);
                u = (Unsafe) theUnsafe.get(Unsafe.class);
            } catch (Exception e) {
                throw new RuntimeException("exception while trying to get Unsafe", e);
            }
        }
        UNSAFE = u;
        long o = 0;
        for (Field f : unsafecl.class.getDeclaredFields()) {
            if (f.getName().equals("field")) {
                o = UNSAFE.staticFieldOffset(f);
                break;
            }
        }
        offset = o;
        clazz = unsafecl.class;
    }


    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            UNSAFE.getInt(clazz, offset);
        }
    }
}
