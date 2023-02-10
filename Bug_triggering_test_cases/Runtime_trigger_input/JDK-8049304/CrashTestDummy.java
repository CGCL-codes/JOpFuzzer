public class CrashTestDummy {

    private static final Object lock = new Object();

    private static final void blocked() {
        System.out.println("about to block forever");
        // Should always be blocked forever.
        synchronized (lock) { }
        // Not supposed to get here.
        throw new RuntimeException("blocked was not blocked");
    }

    public static void main(String[] args) {
        System.out.println("Doing it");
        Thread doit = new Thread(new Runnable() { public void run() { blocked(); }});
        synchronized (lock) {
            doit.start();
            for (int i = 0; true; ++i) {
                System.out.println("main " + Integer.toString(i));
                try { Thread.sleep(100); } catch (InterruptedException ex) { }
            }
        }
    }
}
