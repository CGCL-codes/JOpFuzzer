public class DoDeadlock {
    static final public int N_THREADS = 2;

    public static void main(String[] args) {
        System.out.println("Hello from DoDeadlock!");

        int          t;
        WorkerThread threads[] = new WorkerThread[N_THREADS];

        // create the WorkerThread(s):
        for (t = 0; t < N_THREADS; t++) {
            threads[t] = new WorkerThread("Worker-" + t);
        }

        // start the WorkerThread(s) running:
        for (t = 0; t < N_THREADS; t++) {
            threads[t].start();
        }

        // wait for the WorkerThread(s) to finish:
        for (t = 0; t < N_THREADS; t++) {
            try {
                threads[t].join();
            } catch (InterruptedException ie) {
            }
        }

        System.out.println("Bye from DoDeadlock!");
    }
}


class WorkerThread extends Thread {
    static private Object a_obj = new Object();
    static private Object b_obj = new Object();

    public WorkerThread(String name) {
        super(name);
    }

    public void run() {
        System.out.println("WorkerThread: '" + getName() + "' is running.");

        if (getName().equals("Worker-0")) {
            for (int i = 0; i < 1000; i++) {
                synchronized (a_obj) {
                    try {
                        // give other thread a chance to grab b_obj lock
                        Thread.sleep(1 * 1000);
                    } catch (InterruptedException ie) {
                    }
                    synchronized (b_obj) {
                    }
                }
            }
        } else {
            for (int i = 0; i < 1000; i++) {
                synchronized (b_obj) {
                    synchronized (a_obj) {
                    }
                }
            }
        }

        System.out.println("WorkerThread: '" + getName() + "' is done.");
    }
}
