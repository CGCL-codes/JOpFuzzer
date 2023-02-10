import java.lang.reflect.Field;

public class VarHandleTestAccessBoolean {

    static final jdk.internal.misc.Unsafe UNSAFE;


    static boolean static_v;
    static final Object STATIC_V_BASE;
    static final long STATIC_V_OFFSET;


    boolean v;
    static final long V_OFFSET;


    static {
        try {
            try {
                Field f = jdk.internal.misc.Unsafe.class.getDeclaredField("theUnsafe");
                f.setAccessible(true);
                UNSAFE = (jdk.internal.misc.Unsafe) f.get(null);
            } catch (Exception e) {
                throw new RuntimeException("Unable to get Unsafe instance.", e);
            }

            Field vField = VarHandleTestAccessBoolean.class.getDeclaredField("v");
            V_OFFSET = UNSAFE.objectFieldOffset(vField);

            Field staticVField = VarHandleTestAccessBoolean.class.getDeclaredField("static_v");
            STATIC_V_BASE = UNSAFE.staticFieldBase(staticVField);
            STATIC_V_OFFSET = UNSAFE.staticFieldOffset(staticVField);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        VarHandleTestAccessBoolean t = new VarHandleTestAccessBoolean();
        for (int i = 0; i < 60_000; i++) {
            testInstanceField(t);
        }

        try {
            testStaticFieldUnsafeBoolean();
        } catch (Exception e) {
            System.err.println("FAILED: testStaticFieldUnsafeBoolean");
        }
        try {
            testStaticFieldUnsafeByte();
        } catch (Exception e) {
            System.err.println("FAILED: testStaticFieldUnsafeByte");
        }
        try {
            testStaticFieldBoolean();
        } catch (Exception e) {
            System.err.println("FAILED: testStaticFieldBoolean");
        }
    }

    static void testInstanceField(VarHandleTestAccessBoolean recv) {
        boolean o = UNSAFE.getAndBitwiseAndBoolean(recv, V_OFFSET, false);
        boolean r = UNSAFE.compareAndExchangeBooleanVolatile(recv, V_OFFSET, false, true);
    }

    static void testStaticFieldUnsafeBoolean() {
        static_v = true;

        boolean o = UNSAFE.getAndBitwiseAndBoolean(STATIC_V_BASE, STATIC_V_OFFSET, false);
        if (o != true) throw new RuntimeException();

        boolean x = static_v;
        if (x != (boolean)(true & false)) throw new RuntimeException();
    }

    static void testStaticFieldUnsafeByte() {
        static_v = true;

        byte bo = UNSAFE.getAndBitwiseAndByte(STATIC_V_BASE, STATIC_V_OFFSET, (byte)0);
        boolean o = bo != 0;
        if (o != true) throw new RuntimeException();

        boolean x = static_v;
        if (x != (boolean)(true & false)) throw new RuntimeException();
    }

    static void testStaticFieldBoolean() {
        static_v = true;

        boolean o = getAndBitwiseAndBoolean(STATIC_V_BASE, STATIC_V_OFFSET, false);
        if (o != true) throw new RuntimeException();

        boolean x = static_v;
        if (x != (boolean)(true & false)) throw new RuntimeException();
    }

    public static final boolean getAndBitwiseAndBoolean(Object o, long offset, boolean mask) {
        return byte2bool(UNSAFE.getAndBitwiseAndByte(o, offset, bool2byte(mask)));
    }

    private static boolean byte2bool(byte b) {
        return b != 0;
    }

    private static byte bool2byte(boolean b) {
        return b ? (byte)1 : (byte)0;
    }
}

