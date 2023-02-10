
public class JvmConcurrencyTest {
  volatile int maxThreads = 1000;
  volatile int startedThreads = 0;
  volatile int stoppedThreads = 0;
  volatile boolean pause = true;
  
  final Object startGate = new Object();
  final Object stopGate = new Object();
  final Object bufferGate = new Object();
  
  long updateCount = 0;
  long startTime = 0;
  long stopTime = 0;
  
  Runtime runtime = Runtime.getRuntime();
  
  void run(String[] args) {
    if (args.length == 0)
      System.err.println("Test will be run with " + maxThreads + " threads (default).\n" +
          "Use java JvmConcurrencyTest <max> to specify number of threads." );
    else
      maxThreads = Integer.parseInt(args[0]);
    for (int i=0; i < maxThreads; ++i) {
      try {
        Thread t = new Thread() {
          public void run() {
            synchronized(startGate) {
              ++startedThreads;
              try {
                startGate.wait();
              } catch(InterruptedException e) { ; /* ignore it */ }
            }
            
            for (int i = 0; i < 1000; ++i) {
              synchronized(bufferGate) { updateCount += 1; }
            }

            synchronized(stopGate) {
              ++stoppedThreads;
              while(pause) {
                try { stopGate.wait(); }
                catch(InterruptedException e) { ; /* ignore it */ }
              }
            }
          }
        };
        t.setDaemon(true);
        t.start();
      } catch (java.lang.OutOfMemoryError e) {
        System.err.println("" + i  + " threads created");
        throw e;
      }
    }
   
    synchronized(startGate) {
      while(startedThreads < maxThreads) {
        try { startGate.wait(50); }
        catch(InterruptedException e) { ; /* ignore it */ }
      }
      startTime = System.currentTimeMillis();
      startGate.notifyAll();  // signal all threads to terminate
    }

    synchronized(stopGate) {
      while(stoppedThreads < maxThreads) {
        try {
          stopGate.wait(5);
        } catch (Exception e) { ; /* ignore */ }
      }
      stopTime = System.currentTimeMillis();
      pause = false;
      stopGate.notifyAll();
    }
    System.err.print("\nupdateCount: " + updateCount);
    System.err.println(" Elapsed Time: " + (stopTime-startTime) + " ms\n");
  }
  
  public static void main(String[] args)
  {
    try {
      new JvmConcurrencyTest().run(args);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

