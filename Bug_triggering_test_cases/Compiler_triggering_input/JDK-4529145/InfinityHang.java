public class InfinityHang implements Runnable {
    Thread statusThread = null;
    Thread hangThread = null;
    Thread stopThread = null;

    public static void main(String args[])
    {
        InfinityHang infinityHang = new InfinityHang();

// Start the status thread. This simply tells us the jvm is running.
        infinityHang.statusThread = new Thread(infinityHang);
        infinityHang.statusThread.start();

        try {Thread.sleep(5000);} catch (InterruptedException e) {}

// Start the "hang" thread -- thread in an infinite loop
        infinityHang.hangThread = new Thread(infinityHang);
        infinityHang.hangThread.start();

        try {Thread.sleep(5000);} catch (InterruptedException e) {}

// Start the thread intended to stop the hang thread.
        infinityHang.stopThread = new Thread(infinityHang);
        infinityHang.stopThread.start();

    }

    public void run() {
        if (Thread.currentThread() == statusThread)
        {
            int count = 0;
            while(true) {
                System.err.println("JVM is ok: " + count++);
                try {Thread.sleep(1000);} catch(InterruptedException e) {}
            }
        }
        else if (Thread.currentThread() == hangThread)
        {
            System.err.println("Starting infinite loop.");
            double d = Double.POSITIVE_INFINITY;
            for (; d > 0 ; d -= 360.0);
        }
        else if (Thread.currentThread() == stopThread)
        {
            System.err.println("stopThread running.");
            hangThread.stop();
            System.err.println("stopThread complete.");
        }
    }
}