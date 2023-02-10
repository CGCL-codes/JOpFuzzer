
/**
 *
 * @author michaelellis
 */
public class SumTest {

    public void testAdd() {
        System.out.printf("java.version=%s%n", System.getProperty("java.version"));
        final Sum sum = new Sum();
        for (int i = 1; i <= 100000; ++i) {
            sum.add(1);
            System.out.printf("expected %g, sum.getSum() = %g%n", (double)i, sum.getSum());
        }
    }

    public static void main(String[] args) {
        while(true) {
            new SumTest().testAdd();
        }
    }

}

class Sum {

    double[] sums;

    /**
     * Construct empty Sum
     */
    public Sum() {
        sums = new double[0];
    }

    /**
     * Return the sum of all numbers added to this Sum
     *
     * @return the sum
     */
    final public double getSum() {
        double sum = 0;
        for (final double s : sums) {
            sum += s;
        }

        return sum;
    }

    /**
     * Add a new number to this Sum
     *
     * @param a number to be added.
     */
    final public void add(double a) {
        try {
            sums[sums.length] = -1; // Cause IndexOutOfBoundsException
        } catch (final IndexOutOfBoundsException e) {
            /**
             * SHOW STOPPER
             *
             * By now the variable a can be corrupted!!!!
             */
            final double[] oldSums = sums;
            sums = new double[oldSums.length + 1]; // Extend sums
            System.arraycopy(oldSums, 0, sums, 0, oldSums.length);
            sums[oldSums.length] = a; // Append a

            if (a == 0) {
                System.out.println(a);
                throw new RuntimeException();
            }
        }
    }
}
