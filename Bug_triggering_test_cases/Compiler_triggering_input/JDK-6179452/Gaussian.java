import java.util.Random;

public class Gaussian {
    public static void main(String[] args) {
        int loopSz = Integer.parseInt(args[0]);
        double deviation = 0.0;
        Random rnd = new Random();

        long startTime = System.currentTimeMillis();
        for (int i=0; i<loopSz; i++) {
            double total = 0.0;
            for (int j=0; j<1000000; j++) {
                total += rnd.nextGaussian();
            }
            deviation += total;
        }
        long endTime = System.currentTimeMillis();
        System.out.println(deviation);
        System.out.println("Elapsed time = " + (endTime-startTime) + " msecs");
    }
}