public class InterruptedTest {

    public static void main(String[] args) throws Exception {
        Thread workerThread = new Thread("worker") {
            public void run() {
                System.out.println("Worker thread: running...");
                while (!Thread.currentThread().isInterrupted()) {
                }
                System.out.println("Worker thread: bye");
            }
        };
        System.out.println("Main thread: starts a worker thread...");
        workerThread.start();
        System.out.println("Main thread: waits at most 1s for the worker thread to die...");
        workerThread.join(1000);
        if (workerThread.isAlive()) {
            System.out.println("Main thread: interrupts the worker thread...");
            workerThread.interrupt();
            if (workerThread.isInterrupted()) {
                System.out.println("Main thread: worker thread is interrupted");
            }
            System.out.println("Main thread: waits for the worker thread to die...");
            workerThread.join();
        }
        System.out.println("Main thread: bye");
    }

}