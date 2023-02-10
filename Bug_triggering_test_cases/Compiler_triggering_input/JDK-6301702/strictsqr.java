public class strictsqr {
    static strictfp double strictsqr(double x) {
        return x*x;
    }

    final static int limit = 10000000;
    public static void main(String[] args) {
        for (int i = 0; i < 20; i ++) {
            double x = i / (double)limit;
            System.out.println(strictsqr.strictsqr(x));
        }
    }
}