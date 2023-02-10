public class div0 {
    public static void main(String[] args) {
      for (int i = 0; i < 10000; i++) {
        try {
          System.out.println(idiv());
        } catch (Exception e) {
        }
        try {
          System.out.println(irem());
        } catch (Exception e) {
        }
      }
    }
    static int idiv() {
      int a = 0;
      int b = 1;
      int c = 2;
      int d = 3;
      int e = 4;
      int f = 5;
      return f / a;
    }
    static int irem() {
      int a = 0;
      int b = 1;
      int c = 2;
      int d = 3;
      int e = 4;
      int f = 5;
      return f % a;
    }
  }