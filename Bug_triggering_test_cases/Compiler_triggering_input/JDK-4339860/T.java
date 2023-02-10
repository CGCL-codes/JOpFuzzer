import java.util.Date;

class T extends Thread {

  public void run() {

    try {
System.out.println(new Date());
Thread.sleep(60000);
System.out.println(new Date());
    } catch (Exception e) {
System.out.println(e);
    }
  }
}

public class test {
  public static void main(String argv[]) {
    new T().start();
  }
}
