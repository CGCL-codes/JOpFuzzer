
public class GCStress {
    private static Thread[] threads;

    private static java.util.Random random = new java.util.Random();

    private static int maxBytes;

    private static int numThreads;

    public static void main(String[] args) {
        numThreads = Integer.parseInt(args[0]);
        maxBytes = Integer.parseInt(args[1]);
        threads = new Thread[numThreads];
        for (int x = 0; x < numThreads; x++) {
            threads[x] = new Thread(new Allocator(), "Thread_" + x);
            threads[x].start();
        }

        while (true) {
            for (int x = 0; x < numThreads; x++) {
                if (!threads[x].isAlive()) {
                    threads[x] = new Thread(new Allocator(), "Thread_r" + x);
                    threads[x].start();
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {}

        }
    }

    private static class Allocator implements Runnable {

        public void run() {
            while (true) {
                int tSize = random.nextInt(maxBytes);
                byte[] tMemFiller = new byte[tSize];
                try {
                    Thread.sleep(random.nextInt(200));
                } catch (InterruptedException e) {}
            }
        }

    };
}

