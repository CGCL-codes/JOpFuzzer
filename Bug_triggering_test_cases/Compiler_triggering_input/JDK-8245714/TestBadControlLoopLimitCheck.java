public class TestBadControlLoopLimitCheck {
    public static void main(String[] args) {
        int[] array = {0, 0};
        for (int i = 0; i < 20_000; i++) {
            test(array, 0, 10, false);
            test_helper(42);
        }
    }

    private static int test(int[] a, int start, int stop, boolean flag) {
        int[] b = new int[2]; // non escaping allocation
        System.arraycopy(a, 0, b, 0, 2); // optimized out
        int v = 1;
        int j = 0;
        for (; j < 10; j++);
        int inc = test_helper(j); // delay transformation to counted loop
        // loop limit check here has loads pinned on unc branch
        for (int i = start; i < stop; i += inc) {
            v *= 2;
        }
        if (flag) {
            v += b[0] + b[1];
        }
        return v;
    }

    static int test_helper(int j) {
        return j == 10 ? 10 : 1;
    }
}