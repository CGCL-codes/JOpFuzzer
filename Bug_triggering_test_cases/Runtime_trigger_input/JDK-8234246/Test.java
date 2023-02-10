final inline class MyValue {
    static int cnt = 0;
    final int x;
    final MyValue2 vtField1;
    final MyValue2? vtField2;

    public MyValue() {
        this.x = ++cnt;
        this.vtField1 = new MyValue2();
        this.vtField2 = new MyValue2();
    }

    public int hash() {
        return x + vtField1.x + vtField2.x;
    }
}

final inline class MyValue2 {
    static int cnt = 0;
    final int x;
    public MyValue2() {
        this.x = ++cnt;
    }
}

public class Test {

    static Object test7(Object[] obj) {
        return obj[0];
    }

    public static void main(String[] args) {
        MyValue[] va = new MyValue[1];
        Object[] oa = new Object[1];
        oa[0] = va;
        for (int i = 0; i < 100_000; ++i) {
          if (test7(((i % 2) == 0) ? va : oa) != va[0]) throw new RuntimeException("FAIL");
        }
    }
}
