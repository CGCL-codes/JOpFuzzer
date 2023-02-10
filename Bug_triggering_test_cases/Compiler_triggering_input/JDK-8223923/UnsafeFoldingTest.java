import java.lang.reflect.Field;

import sun.misc.Unsafe;

public class UnsafeFoldingTest {
    private static final Unsafe UNSAFE = initUnsafe();

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

    public static class TestClassInt {
        public int x;
        public int y;
        public int z;

        public TestClassInt() {
            this(0, 0);
        }

        public TestClassInt(int x) {
            this(x, 0);
        }

        public TestClassInt(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            TestClassInt other = (TestClassInt) obj;
            return x == other.x && y == other.y && z == other.z;
        }

        @Override
        public String toString() {
            return "{" + x + "," + y + "," + z + "}";
        }

        @Override
        public int hashCode() {
            return x + 13 * y;
        }

        public static final long fieldOffset1;
        public static final long fieldOffset2;
        public static final boolean firstFieldIsX;

        static {
            try {
                long localFieldOffset1 = UNSAFE.objectFieldOffset(TestClassInt.class.getField("x"));
                // Make the fields 8 byte aligned (Required for testing setLong on Architectures
                // which does not support unaligned memory access. The code has to be extra careful
                // because some JDKs do a better job of packing fields.
                if (localFieldOffset1 % 8 == 0) {
                    fieldOffset1 = localFieldOffset1;
                    fieldOffset2 = UNSAFE.objectFieldOffset(TestClassInt.class.getField("y"));
                    firstFieldIsX = true;
                } else {
                    fieldOffset1 = UNSAFE.objectFieldOffset(TestClassInt.class.getField("y"));
                    fieldOffset2 = UNSAFE.objectFieldOffset(TestClassInt.class.getField("z"));
                    firstFieldIsX = false;
                }
                assert fieldOffset2 == fieldOffset1 + 4;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Side effect to create a deopt point, after possible virtualization.
    static int sideEffectField;

    private static void sideEffect() {
        sideEffectField = 5;
    }

    public static int unsafeSnippet1(double i1, boolean c) {
        TestClassInt a = new TestClassInt();
        UNSAFE.putDouble(a, TestClassInt.fieldOffset1, i1);
        if (c) {
            sideEffect();
        }
        return UNSAFE.getInt(a, TestClassInt.fieldOffset1) + UNSAFE.getInt(a, TestClassInt.fieldOffset2);
    }

    public static void main(String[] args) {
        TestClassInt a = new TestClassInt();
        sideEffect();
        System.out.println(unsafeSnippet1(1.0, false));
        System.out.println(unsafeSnippet1(1.0, false));
    }
}

