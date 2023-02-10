public class test {

    float correct_value = 0.0f;
    static float correct_values[] = new float[64];

    public static void main (String args[]) {
        for (int i = 0; i < correct_values.length; i++) {
            correct_values[i] = 0.0f;
        }
        // Run enough times to trigger compilation
        for (int i = 0; i < 20000; i++) {
            if (0 != run()) {
                System.out.println("Fails on teration " + i);
                System.exit(96);
            }
        }
    };


    public static int run () {
        return (new test()).reallyRun();
    };

    boolean print_failure(int width, float test_value, long tricky_bits) {
        // Save the correct values the first time through, i.e., when we interpret.
        if (correct_values[width] == 0.0) {
            correct_values[width] = test_value;
        }
        // Compare against the "correct" value on each iteration.
        if (test_value != correct_values[width]) {
            System.out.println("");
            System.out.println("test failed");
            System.out.println("width=" + width);
            System.out.println("test_value: " + test_value + " " + Integer.toHexString(Float.floatToIntBits(test_value)));
            System.out.println("instead of: " + correct_values[width] + " " + Integer.toHexString(Float.floatToIntBits(correct_values[width])));
            System.out.println("tricky_bits: " + Long.toHexString(tricky_bits));
            System.out.println("");

            return false;
        }

        return true;
    }

    float do_test(float width_shift, float diff_shift, long tricky_bits) {
        float test_value = (float)tricky_bits;
        // test_value /= diff_shift;
        // test_value /= width_shift;

        return test_value;
    }

    int reallyRun () {
        boolean OK = true;
        int format_std_width = 24; // single-standard mantissa width
        int format_min_width = 32; // shortest single-extended mantissa
        int format_max_width = 62; // need 63-bit positive long to check format 62

// If actual float format has mantissa wider than format_max_width
// bits, this test does not checks correctness of the implementstion.

        correct_value = Float.intBitsToFloat(0x3f800001); // 1+epsilon

        for (int format_width=format_min_width;
             format_width<=format_max_width;
             format_width++) {

            int diff = format_width - format_std_width;
            long tricky_bits = (((1L << format_std_width) + 1) << diff) + 1;

            float test_value = do_test((float) (1L << format_std_width), (float) (1L<<diff), tricky_bits);

            OK = print_failure(format_width, test_value, tricky_bits);
        };

        return (OK? 0/*STATUS_PASSED*/: 2/*STATUS_FAILED*/);
    };

}