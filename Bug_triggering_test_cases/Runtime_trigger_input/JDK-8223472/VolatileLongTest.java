public class VolatileLongTest {
    static final long timeout = 1000;
    static volatile boolean stop = false;

    class MyThread extends Thread {
        volatile long myLong = 0;
        MyThread peer;
        public synchronized void run() {
            long startTime = System.currentTimeMillis();
            while (true) {
                for (long i = 0; i < 1024; i++ ) {
                    myLong = (i << 32) | i;
                    long peerLong = peer.myLong;
                    if ((peerLong >> 32) != (peerLong & 0xFFFF)) {
                        System.out.println("value not consistent: " + Long.toHexString(peerLong));
                        stop = true;
                    }
                }
                if (System.currentTimeMillis() - startTime >= timeout) { stop = true; }
                if (stop) { return; }
            }
        }
    }

    public static void main(String args[]) {
        new VolatileLong().test();
    }    

    void test() {
        MyThread thread1 = new MyThread();
        MyThread thread2 = new MyThread();
        thread1.peer = thread2;
        thread2.peer = thread1;
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {}
    }
}
