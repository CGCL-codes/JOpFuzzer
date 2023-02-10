import java.io.*;

public class Speed {
    static class Evaluator {
        public final double eval(double i) {
            return (i * 0.123456) / (i + 1.0);
        }
    }

    Object obj; // used for isA tests
    static double r;

    public static void main(String[] args) {
        Speed sp = new Speed(args);
    }

    public Speed(String[] args) {
        obj = this;
        int ns = args.length;
        int niters = 3;
        for (int i = 0; i < ns; i++) {
            try {
                niters = Integer.parseInt(args[i]);
            } catch (Exception ex) {
            }
        }
        run(niters);
    }

    static void printTime(String mes, long time, double v) {
        System.out.print(" Testing " + mes + " : ");
        System.out.println((time / 1000.0) + " s.");
        if (v > 0.0)
            System.out.println(" result = " + v);
        System.out.println();
    }

    public void run(int idx) {
        final int ncycle = idx;
        long start = System.currentTimeMillis();
        int k, i, n = (int) 1E7;
        for (k = 1; k <= ncycle; k++) {
            System.out.println();
            System.out.println(" --------------------------" + Integer.toString(k)
                    + "--------------------------------");
            System.out.println();
            System.out.println();

            long iter_time = 0;
            // Test # 2
            iter_time += testDoubleOpsViaFunctionCall((int) 1E8);
            // Test # 3
            iter_time += testInlinedDoubleOps((int) 1E8);
            // Test # 4
            iter_time += testIntOperations((int) 1E9);

        }
    }

    // double operation with function call
    long testDoubleOpsViaFunctionCall(int n) {
        double r = 0.0;
        Evaluator evaluator = new Evaluator();
        long start = System.currentTimeMillis();
        for (int i = 1; i <= n; i++)
            r += evaluator.eval((double) i);
        long time = System.currentTimeMillis() - start;
        printTime(n + " double moltiplications via function call", time, r);
        return time;
    }

    // Test # 3 -B
    long testInlinedDoubleOps(int n) {
        int i;
        double r = 0.0;

        long start = System.currentTimeMillis();
        for (i = 1; i <= n; i++)
            r += (i * 0.123456) / (i + 1.0);
        long time = System.currentTimeMillis() - start;
        printTime(n + " inlined double moltiplications", time, r);
        return time;
    }

    long testIntOperations(int n) {
        int i, a = 0, b = 2;
        long start = System.currentTimeMillis();
        for (i = 1; i <= n; i++)
            a += a - i * 3;
        long time = System.currentTimeMillis() - start;
        printTime(n + " int moltiplications + additions", time,
                (double) a);
        return time;
    }

}
