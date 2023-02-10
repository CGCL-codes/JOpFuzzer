import sun.misc.Unsafe;

public class AllocateInstance {
    public static final Unsafe UNSAFE;
    static {
        try {
            java.lang.reflect.Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            UNSAFE = (Unsafe) theUnsafe.get(Unsafe.class);
        } catch (Exception e) {
            throw new RuntimeException("exception while trying to get Unsafe", e);
        }
    }
    
    static Class<?> cl = Object.class;

    static Object o;

    public static void main(String[] args) throws Exception {
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 100000; i++) {
                try {
                    o = UNSAFE.allocateInstance(cl);
                } catch (Exception e) {
                    if (j == 0) {
                        throw e;
                    }
                }
            }
            cl = Object[].class;
        }
    }
}
