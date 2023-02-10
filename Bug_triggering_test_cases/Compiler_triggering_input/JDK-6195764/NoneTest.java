public class NoneTest extends Thread {
    void foo(String x) {
        try {
            x.toString();
        } catch (Exception ee) { }
    }

    public void run() {
        for (;;) {
            for (int i = 1; i < 10000; i++) {
                foo((i % 100 == 0)? "" : null);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t = new NoneTest();
        t.start();
        Thread.sleep(20000); // 20 sec
        System.out.println("bye");
        // t.interrupt();
        Thread.sleep(2000); // 2 sec
        System.exit(0);
    }
}