public class bw implements Runnable {
  public void run() {
    for (int i = 0; i < 50; i++) {
      try {
        System.gc();
        Thread.sleep(50);
      } catch (Exception e) {
      }
    }
    System.exit(0);
  }

  static int iters = 10000;
  public static void main(String[] args) {
    Thread t = new Thread(new bw());
    t.start();
    test(iters);
  }
  public static void test(int limit) {
    int i = 0;
    while (true) {
      double f = 32923849234.9;
      double f1 = f * f * f;
      i++;
    }
  }
}