
// Name:        TimeJumpWithThreadJoin.java
//
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TimeJumpWithThreadJoin {
    public static void main(String[] args) throws InterruptedException {
        SleepWorker thread = new SleepWorker("Worker0");
        thread.start();
        System.out.println("Waiting for thread join (should last for one minute)...");
        System.out.println("Meantime, set your system clock back (for ten minutes, for example) and measure time (on real clock) till Thread.join ended.");
        thread.join(60 * 1000);
        System.out.println("Thread.join ended().");
        if (thread.isAlive()) {
            thread.interrupt();
        }
        System.out.println("Done.");
    }
}

class SleepWorker extends Thread {
    static final public int SLEEP_TIME = 60 * 60 * 1000;  // 1 hour
    public SleepWorker(String name) {
        super(name);
    }

    public void run() {
        System.out.println("Thread goes to sleep.");
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException ie) {
            System.out.println("Thread sleep interrupted.");
        }
        System.out.println("SleepWorker: '" + getName() + "' is done.");
    }
}

