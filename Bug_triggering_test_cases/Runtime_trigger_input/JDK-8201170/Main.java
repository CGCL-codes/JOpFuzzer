
import java.util.ArrayList;

public class Main {

    public static class LockingRunnable implements Runnable {

        @Override
        public void run() {
            Object object = new Object();
            synchronized (object) {
                try {
                    object.wait();
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        LockingRunnable lockingRunnable = new LockingRunnable();
        new Thread(lockingRunnable).start();
        ArrayList<Thread> arrayList = new ArrayList(100);

        while (true) {

            Object lock = new Object();
            synchronized (lock) {

                lock.wait(50);
                lockingRunnable = new LockingRunnable();
                Thread thread = new Thread(lockingRunnable);
                arrayList.add(thread);
                thread.start();

                if (arrayList.size() > 100) {
                    arrayList.stream().forEach(item->item.interrupt());
                    arrayList.clear();
                }
            }
        }
    }
}

