import sun.misc.Unsafe;
import java.lang.reflect.Field;
import java.util.concurrent.CyclicBarrier;

public class BusyLock implements Runnable {
    private static Unsafe unsafe;

    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static final int iterations = 1;
    public static final int timeout = 1000;

    public static CyclicBarrier barrier = new CyclicBarrier(2);

    public static int f = 0;
    protected Object monitor = new Object();

    @Override
    public void run() {
        for (int i = 0; i < iterations; i++) {
            try {
                // wait until forceAbort leave monitor
                barrier.await();
                if(unsafe.tryMonitorEnter(monitor)) {
                    barrier.await();
                    Thread.sleep(timeout);
                    unsafe.monitorExit(monitor);
                } else {
                    throw new RuntimeException("Monitor should be entered by " +
                                               "::run() first.");
                }
            } catch (Throwable t) {
                throw new RuntimeException(t);
            }
        }
    }

    public void lock() {
        try {
            // wait for a new monitor if we're running a test on a
            // stack lock
            barrier.await();
            // wait until monitor is locked by a ::run method
            barrier.await();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        synchronized(monitor) {
            f++;
        }
    }

    public Object getMonitor() {
        return monitor;
    }

    public void setMonitor(Object monitor) {
        this.monitor = monitor;
    }


    public static void main(String args[]) throws Exception {
        BusyLock busyLock = new BusyLock();
        busyLock.setMonitor(new Object());
        Thread t = new Thread(busyLock);
        t.start();
        for (int i = 0; i < BusyLock.iterations; i++) {
            busyLock.lock();
        }
        t.join();
    }
}
