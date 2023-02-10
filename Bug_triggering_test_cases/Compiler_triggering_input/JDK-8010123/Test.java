

public class Test {

  public static void main(String[] args) {
   long iters = 0;
    while (true) {
      Test t = new Test();
      t.run();
      iters++;
      if (iters % 1000 == 0) {
          System.out.print(".");
      }
    }
  }

  public Test() {

  }

  public void run() {
    Counter counter = new Counter();
    counter.initialize();
    int i;
    while( (i = counter.nextRow()) != -1){
      if (i == -1) {
        System.err.println("  i == -1 ==> error!!!!!!!!!");
        throw new RuntimeException("Failed.");
      }
    }
  }

  public static class Counter {
    int rowCount;
    int i;

    public void initialize(){
        rowCount = 100;
        i = 0;
    }

    public int nextRow() {
        if (++i > rowCount)
            return -1;
        return i;
    }
  }
}

