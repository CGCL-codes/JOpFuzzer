public class TestUnexpectedLoadOrdering {

    public static void main(String[] args) {
        double[] array1 = new double[1000];
        double[] array2 = new double[1000];
        for (int i = 0; i < 20_000; i++) {
            test(array1, array2);
        }
    }

    private static double test(double[] array1, double[] array2) {
        double res = 0;
        for (int i = 0; i < array1.length; i++) {
            array2[i] = i;
            res += array1[i];
        }
        return res;
    }
}