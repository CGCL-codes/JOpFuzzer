public class PowTest {

    public static void main(final String[] args) {
        double b = 1.0 / 3.0;
        double e = 2.0;
        double r = Math.pow(b, e);
        double n = b * b;
        // Find a base where pow(b, 2) != b * b
        while (r == n) {
            b += 1.0 / 3.0;
            n = Math.pow(b, e);
            r = b * b;
        }
        System.out.println("found b=" + b + " n=" + n + " r=" + r);
        r = n = Math.pow(b, e);
        // Wait until pow gets compiled into x * x
        while (r == n) {
            n = Math.pow(b, e);
        }
        System.out.println("bad b=" + b + " n=" + n + " r=" + r);
    }
}