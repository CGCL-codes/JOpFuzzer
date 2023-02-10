
public class Test {
    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            public void run() {
                for (;;) {
                    synchronized (this) { }
                }
            }
        };
        new Thread(runnable).start();
        new Thread(runnable).start();
    }
}
