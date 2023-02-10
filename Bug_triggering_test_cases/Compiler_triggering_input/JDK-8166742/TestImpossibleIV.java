public class TestImpossibleIV {

    static private void testMethod() {
        int sum = 0;
        // A unit count-down loop which has an induction variable with
        // MIN_VALUE stride.
        for (int i = 100000; i >= 0; i--) {
            sum += Integer.MIN_VALUE;
        }
    }

    public static void main(String[] args) {
        testMethod();
    }
}
