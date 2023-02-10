
import java.util.ArrayList;
import java.util.List;

class Hello {
    private static int doIt(int i) throws InterruptedException {
        if (i == 4000) {
            synchronized (Hello.class) {
                System.out.println("Waiting");
                Hello.class.wait();
                System.out.println("Waking up");
            }
            return i;
        }
        return i + doIt(i + 1);
    }

    public static void main(String[] args) throws Exception {
        for (int t = 0; t < 1; t++) {
            List<Thread> threads = new ArrayList<>();
            for (int i = 0; i < 300; i++) {
                threads.add(new Thread(() -> {
                    try {
                        doIt(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }));
            }
            for (Thread thread : threads) {
                thread.start();
            }

            System.in.read();

            synchronized (Hello.class) {
                Hello.class.notifyAll();
            }
            for (Thread thread : threads) {
                thread.join();
            }
            threads.clear();
            System.out.println("GC");
            System.gc();
            System.in.read();
        }
    }
}

