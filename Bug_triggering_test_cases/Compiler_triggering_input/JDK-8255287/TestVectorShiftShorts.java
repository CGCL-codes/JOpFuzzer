public class TestVectorShiftShorts {

    private static final int ARRLEN = 1000;
    private static final int ITERS = 20000;

    public static void main(String args[]) {
        short[] a0 = new short[ARRLEN];
        short[] a1 = new short[ARRLEN];

        // Initialize
        test_init(a0, a1);

        // Warmup
        for (int i = 0; i < ITERS; i++) {
            test_lshift(a0, a1);
            test_urshift(a0, a1);
        }

        // Test and verify results
        test_init(a0, a1);
        test_lshift(a0, a1);
        verify_lshift(a0, a1);

        test_init(a0, a1);
        test_urshift(a0, a1);
        verify_urshift(a0, a1);

        // Finish
        System.out.println("Test passed");
    }

    static void test_init(short[] a0, short[] a1) {
        for (int i = 0; i < ARRLEN; i++) {
            a0[i] = (short)(i & 3);
            a1[i] = (short)i;
        }
    }

    static void test_lshift(short[] a0, short[] a1) {
        for (int i = 0; i < ARRLEN; i++) {
            a0[i] = (short)(a1[i] << 10);
        }
    }

    static void verify_lshift(short[] a0, short[] a1) {
        for (int i = 0; i < ARRLEN; i++) {
            if (a0[i] != (short)(a1[i] << 10)) {
                throw new RuntimeException("LShift test failed.");
            }
        }
    }

    static void test_urshift(short[] a0, short[] a1) {
        for (int i = 0; i < ARRLEN; i++) {
            a0[i] = (short)(a1[i] >>> 10);
        }
    }

    static void verify_urshift(short[] a0, short[] a1) {
        for (int i = 0; i < ARRLEN; i++) {
            if (a0[i] != (short)(a1[i] >>> 10)) {
                throw new RuntimeException("URshift test failed.");
            }
        }
    }

}
