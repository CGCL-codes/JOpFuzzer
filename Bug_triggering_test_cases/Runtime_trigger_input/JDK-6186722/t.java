
public class t
{
   static public void main(String[] args) {
      while (true) {
         new tt();

         ThreadGroup rootGrp = Thread.currentThread().getThreadGroup();
         while (rootGrp.getParent() != null)
            rootGrp = rootGrp.getParent();
   
         Thread[] threads = new Thread[rootGrp.activeCount() + 8];
         int count = rootGrp.enumerate(threads, true);
         System.err.print("count="+count);
         for (int i = 0; i < count; ++i) {
            Thread t = threads[i];
            int tmp = t.countStackFrames();     // ************ causes the crash
            System.err.print(tmp+" ");
         }
         System.err.println();
      }
   }
}

class tt implements Runnable
{
        private Thread runner;

        public tt() {
                runner = new Thread(this, "tt");
                runner.setDaemon(true);
                runner.start();
        }

        public void run() {
                if (Thread.currentThread() == runner) {
                        try {
                                Thread.sleep(10);
                        } catch (InterruptedException e) {}
                }
        }
}

