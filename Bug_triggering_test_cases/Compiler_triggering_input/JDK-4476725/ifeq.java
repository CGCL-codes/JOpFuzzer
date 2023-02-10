public class ifeq implements Runnable {
    public void run() {
        //for (int i = 0; i < 50; i++) {
        while (true) {
            try {
                System.gc();
                Thread.sleep(50);
            } catch (Exception e) {
            }
        }
// System.exit(0);
    }

    public static void main(String[] args) {
        Thread t = new Thread(new ifeq());
        t.start();
        ifeq();
        constant_if();
    }

    public static void ifeq() {
        int count = 100000;
        int r = 0;
        try {
            while (r == 0) {
                double d = 2394829834.0;
                double d2 = d;
                count--;
                if (count == 0) {
                    return;
                }
            }
            return;
        } catch (Exception e) {
        }
    }

    public static int zero() { return 0; }

    public static void constant_if() {
        while (zero() == 0) {
        }
    }
}