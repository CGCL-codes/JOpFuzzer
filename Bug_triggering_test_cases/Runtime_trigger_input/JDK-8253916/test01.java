/*
 * @test
 *
 * @library /test/lib
 *
 * @run main test01
 */

public class test01 {

    static final long MAX_ITERATIONS = 2000000L; // Resasonable limit on number of threads
    static final long TIMEOUT = 100000;          // In ms

    static Object hanger = new Object();
    static volatile boolean threadsDone;

    static Thread makeThread() {
        final Thread thr = new Thread(new Runnable() {
            public void run() {
                while (!threadsDone) {
                    try {
                        synchronized (hanger) {
                            hanger.wait();
                        }
                    } catch (InterruptedException ignored) {}
                }
            }
        }, "fleece");
        thr.start();
        return thr;
    }

    public static void main(String[] args) {
        int count = 0;
        threadsDone = false;
        long startTime = System.currentTimeMillis();

        System.out.println("Creating threads...");
        try {
            while (count < MAX_ITERATIONS && System.currentTimeMillis() - startTime < TIMEOUT) {
                makeThread();
            }

            System.out.println("Can't reproduce OOME due to a limit on iterations/execution time. Test was useless.");
            System.out.println("time: " + (System.currentTimeMillis() - startTime) + "ms");
            System.out.println("count = " + count);

        } catch (OutOfMemoryError e) {
            System.out.println("OOM: time: " + (System.currentTimeMillis() - startTime) + "ms");
            System.out.println("count = " + count);
        } finally {
            threadsDone = true;
            synchronized (hanger) {
                hanger.notifyAll();
            }
        }
    }
}
