public class OwnTests {

  public static void main(String[] args) {
    MyValueType v = MyValueType.testDefault();

    for (int i = 0; i < 1000000; i++)
      MyValueType.testBranchArg1(false, v);
  }
}

__ByValue final class MyValueType {
  final int i;
  final int j;

  private MyValueType()
  {
    this.i = 0;
    this.j = 0;
  }

  __ValueFactory static MyValueType testDefault() {
    return __MakeDefault MyValueType();
  }

  __ValueFactory static MyValueType testBranchArg1(boolean flag, MyValueType v1) {
    if (flag) {
      v1.i = 3;
      v1.j = 4;
    }
    return v1;
  }
}
