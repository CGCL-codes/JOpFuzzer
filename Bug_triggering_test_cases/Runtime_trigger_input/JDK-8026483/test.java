
public class MachPortsTest extends Thread {
  private class DelayedThread extends Thread {
    private int m_delay_ms;

    public DelayedThread(int delay_ms) {
      m_delay_ms = delay_ms;
    }

    public void run() {
      try {
        Thread.sleep(m_delay_ms);
      } catch (Exception ex) {
        System.out.println("Delayed thread interrupted");
      }
    }
  }

  public MachPortsTest() throws Exception {
    long startTime;
    long duration_ns;
    long duration_ns_record = 0;

    while(true) {
      startTime = System.nanoTime();
      new DelayedThread(100).start();
      duration_ns = System.nanoTime() - startTime;
      if (duration_ns > duration_ns_record) {
        duration_ns_record = duration_ns;
        System.out.println("duration: " + duration_ns + "ns");
      }
      Thread.sleep(1);
    }
  }

  public static void main(String args[]) throws Exception {
    new MachPortsTest();
  }
}

