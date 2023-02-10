
public class Test extends Thread {

  public static void main(String[] args) {
    int i=0;
    while (true) {
      System.out.println("Starting Thread #" + (i+1));
      try {
        new Test().start();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
      i++;
    }
  }
  
  public void run() {
    try {
      Thread.sleep(1000000);
    }
    catch (Exception e) {
    }
    System.out.println("Ende");
  }
  
}

