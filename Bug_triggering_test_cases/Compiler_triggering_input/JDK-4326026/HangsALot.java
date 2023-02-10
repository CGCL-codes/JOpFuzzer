public class HangsALot
{
    boolean finished = false;
    boolean started = false;
    Object t = new Object();

    class V implements Runnable {
        public void run () {
            synchronized (t) { started = true; }
            boolean done = false;
            do {
              synchronized (t) { done = finished; }
            } while (done == false);
            System.out.println ("V done");
        }
    }

    public void test() {
      Thread t1 = new Thread(new V(), "v");
      t1.start();
      try {
         int i = 0;
         boolean running = false;
         while (running == false) {
           System.out.println ("waiting: " + (i++));
           Thread.sleep(1000);
           synchronized (t) { running = started; }
         }
         System.out.println ("t1 is running.");
      }
      catch (Exception e) {
      }
      
      synchronized (t) { finished = true; }
      try { t1.join(); } catch (InterruptedException x) {}
      System.out.println ("done");
    }

    public static void main(String argv[]) {
      new HangsALot().test();
    }
}