import java.util.Random;

public class Main {

    public static void main(String[] args) throws Exception {

        while (true) {

            final Random random = new Random();
            final double[] values = new double[100_000_000];
            for (int i = 0; i < values.length; i++)
                values[i] = random.nextDouble();

            System.gc();

            final long start = System.currentTimeMillis();

            double blackhole = 0;
            for (int i = 0; i < values.length; i++)
                blackhole += Math.pow(values[i], 2);

            final long elapsed = System.currentTimeMillis() - start;

            System.out.println(elapsed + "ms (" + blackhole + ")");
        }
    }
}

