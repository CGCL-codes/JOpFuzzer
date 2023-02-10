public class Test1 {

    private static double test(double d, long limit) {
        for (long i = 0; i < limit; i++) { // 'long' - not-countable loop
            d = Math.sin(d);
        }
        return d;
    }

    public static void main(String args[]) {
        double d = 0.3;
        d = test(d, 10000); // warmup
        d = test(d, 100000000);
    }
}
