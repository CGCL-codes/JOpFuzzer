public class IterationSplitPredicateInconsistency {
    static volatile int barrier;

    static boolean test1_helper(int start, int stop, double[] array1, double[] array2, int exit) {
        for (int i = start; i < stop; i++) {
            array1[i] = array2[i];
            if (i == exit) {
                return true;
            }
            barrier = 0x42;
        }
        return false;
    }

    static double[] test1(int start, double[] array2, int exit) {
        double[] array1 = new double[10];
        if (test1_helper(start, 5, array1, array2, exit)) {
            return null;
        }
        return array1;
    }
    public static void main(String[] args) {
        double[] array2 = new double[10];
        double[] array3 = new double[1000];
        for (int i = 0; i < 20_000; i++) {
            test1_helper(0, 1000, array3, array3, 998);
            test1(0, array2, 999);
            test1(0, array2, 4);
        }
    }
}