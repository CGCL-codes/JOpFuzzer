
public class JvmThreadTest {
  int maxThreads = 100;
  int startedThreads = 0;
  final Object startGate = new Object();
  final Object stopGate = new Object();
  boolean pause = true;
  Runtime runtime = Runtime.getRuntime();
  
  void displayMemoryStats() {
    System.err.println("Runtime.totalMemory(): " + runtime.totalMemory());
    System.err.println("Runtime.maxMemory():   " + runtime.maxMemory());
    System.err.println("Runtime.freeMemory():  " + runtime.freeMemory());
  }
  
  void run(String[] args) {
    if (args.length > 0) maxThreads = Integer.parseInt(args[0]);
    for (int i=0; i < maxThreads; ++i)
    {
      try {
        Thread t = new Thread() {
          public void run() {
            try {
              synchronized(startGate) {
                ++startedThreads;
                startGate.wait();
              }
            } catch(InterruptedException e) { ; /* ignore it */ }
            
            synchronized(stopGate) {
              // signal that we are done
              --startedThreads;

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
        displayMemoryStats();
        throw e;
      }
    }
    System.err.println("" + maxThreads + " threads created");
    
    synchronized(startGate) {
      while(startedThreads < maxThreads) {
        try { startGate.wait(50); }
        catch(InterruptedException e) { ; /* ignore it */ }
      }
      System.err.println("" + startedThreads + " threads started");
      displayMemoryStats();
      startGate.notifyAll();  // signal all threads to terminate
    }
    synchronized(stopGate) {
      while(startedThreads > 0) {
        try {stopGate.wait(5); }
        catch(InterruptedException e) { ; /* ignore */ }
      }
      pause = false;
      stopGate.notifyAll();
    }
    System.err.println("" + maxThreads + " threads stopped");
  }
  
  public static void main(String[] args)
  {
    try {
      new JvmThreadTest().run(args);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
/*  Use the following cmd script to execute

setlocal
set JAVA_HOME=c:\java\j2sdk1.4.2_04
set PATH=%java_home%\bin;%path%
javac JvmThreadTest.java
Java -showversion JvmThreadTest 1800
Java -showversion JvmThreadTest 4000

set JAVA_HOME=c:\java\j2sdk1.5.0
set PATH=%java_home%\bin;%path%
javac JvmThreadTest.java
Java -showversion JvmThreadTest 1800
Java -showversion JvmThreadTest 4000


*/
