public class Reduced {
  int iArr[] = new int[0];

  void test() {
    int x = 8;
    try {
      for (int i = 0; i < 8; i++) {
        iArr[1] = 9;
        for (int j = -400; 1 > j; j++) {
          iArr[j] = 4;
          x -= 2;
        }
      }
    } catch (ArrayIndexOutOfBoundsException e) {
    }
  }
  public static void main(String[] k) {
      Reduced t = new Reduced();
      for (int i = 0; i < 3; i++) {
        t.test();
      }
  }
}
