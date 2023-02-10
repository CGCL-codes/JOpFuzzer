      
public class Test {

  static volatile boolean loop = true;

  public static void test(Object obj) {
      for (int i = 0; i == 0; ) {
          while (true) {
              int val = (Integer)obj;
              if (val == i++) break;
              if (!loop) break;
          }
      }
  }

  public static void main(String[] args) {
      test(100_000);
  }

}

