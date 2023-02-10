public class OwnTests {

  public static void main(String[] args) {
    MyValueType v = MyValueType.testDefault();

    for (int i = 0; i < 1000000; i++) {
      MyValueType.testBranchAllocation5(i % 2 == 0, v);
    }
  }
}

__ByValue final class MyValueType {
  final int i;
  final int j;

  private MyValueType() {
    this.i = 0;
    this.j = 0;
  }

  __ValueFactory static MyValueType testDefault() {
    return __MakeDefault MyValueType();
  }

  __ValueFactory static MyValueType testBranchAllocation5(boolean flag, MyValueType v1) {
    MyValueType vt;
    if (flag) {
      vt = __MakeDefault MyValueType();
      useVT(vt);
      vt.i = 3;
    } else {
      vt = __MakeDefault MyValueType();
    }

    if (flag) {
      useInt(vt.i);
    } else {
      useInt(vt.j);
    }
   
    useVT(vt);
    return v1;
  }
 
  static int useInt(int i) { return i; }

  static MyValueType useVT(MyValueType vt) { return vt; }
}
