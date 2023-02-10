
public class Test {

public static void main(String[] args) {
  Test t = new Test();
  t.execute();
}

public void execute() {
  synchronized(this) {
    System.out.println("This is a test");
  }
}

}
