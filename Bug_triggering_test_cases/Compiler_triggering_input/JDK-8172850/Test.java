public class Test {
  private static Test f1;
  private static Test f2;

  // Run with -XX:CompileCommand=compileonly,Test::test1 -XX:CompileCommand=dontinline,Test::m1
  public static void main(String args[]) {
    f2 = new Test();
    for (int i = 0; i < 100_000; ++i) {
      f2.test1(f2);
    }
  }

  public void m1() { }

  public void m2() { }

  public void test1(Test obj) {
    try {
      m1();
    } catch (Exception e) {

    } finally {
      f1 = obj;
    }
    f2.m2();
  }

  public void test2(Test obj) {
    try {
      m1();
    } finally {
      f1 = obj;
    }
    f2.m2();
  }
}
