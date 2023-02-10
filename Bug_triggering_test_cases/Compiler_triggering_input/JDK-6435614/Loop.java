public class Loop {

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 1000000; i++)
            f();
        System.out.println("done...");
    }


    // a fake function to always return 2, but to prevent the optimizer from knowing this...
    private static int length() {
        double x = Math.random();
        if (x < 2)
            return 2;
        else
            return 10;
    }


    private static void f() {
        final int lag = 3;

        double[] nl = new double[length()];
        double[] ol = new double[253];
        double[] w = new double[nl.length];

        for(int i = 0; i < nl.length; i++) {
            try {
                if (i < lag)
                    w[i] = ol[ol.length - lag + i];
                else
                    w[i] = nl[i-lag];
            } catch (Exception e) {
// the index i is 2, eve though that shouldn't be possible
// because the array length of nl is always 2.
// we're failing on the indexing of w[2]
                System.err.println("i="+i);
                System.err.println("index="+(ol.length - lag + i));
                System.err.println("w.length="+w.length);
                System.err.println("ol.length="+ol.length);
                System.err.println("nl.length="+nl.length);
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
}