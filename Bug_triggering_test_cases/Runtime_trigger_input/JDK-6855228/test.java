
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Troy T. Collinsworth
 */
public class TestNanoTime implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        int threads = 30;

        final ExecutorService es = Executors.newFixedThreadPool(threads);
        for (int i = 0; i < threads; i++) {
            es.execute(new TestNanoTime());
        }
        Thread.sleep(30000000);
        System.out.println("Completed test, exiting");
        System.exit(0);
    }

    public void run() {
        int loopSize = 10;
        long[] times = new long[loopSize];

        int consec = 0;
        int maxConsec = 0;
        int currConsec = 0;
        long a = System.nanoTime();
        long b = System.nanoTime();
        long minDiff = b - a;
        long maxDiff = b - a;

        String changed = null;
        while (true) {
            for (int i = 0; i < loopSize; i++) {
                times[i] = System.nanoTime();
            }
            a = b;
            b = System.nanoTime();
            long newDiff = b - a;
            if (newDiff < minDiff) {
                minDiff = newDiff;
                changed = "minDiff";
            }
            if (newDiff > maxDiff) {
                maxDiff = newDiff;
                changed = "maxDiff";
            }
            if (a == b) {
                ++consec;
                if (consec > currConsec) {
                    currConsec = consec;
                }
                if (currConsec > maxConsec) {
                    maxConsec = currConsec;
                    changed = "maxConsec";
                }
            } else {
                consec = 0;
            }
            if (changed != null) {
                System.out.println(Thread.currentThread().getName() + " maxConsec=" + maxConsec + " minDiff=" + minDiff
                        + " maxDiff=" + maxDiff + " a=" + a + " b=" + b + " changed=" + changed);
                changed = null;
            }
        }
    }
}

