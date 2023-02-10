import java.lang.invoke.*;
import java.lang.reflect.Method;
import java.util.Arrays;

public class TestLWorld  {

    public static void main(String[] args) throws Throwable {
        TestLWorld t = new TestLWorld();
        for (int i = 0; i < 100000; i++)
            t.test9();
    }

    public static abstract class MyAbstract {
    }

    static final primitive class MyValue2Inline {
        final double d;
        final long l;

        public MyValue2Inline(double d, long l) {
            this.d = d;
            this.l = l;
        }
    }

    public static final primitive class MyValue2 extends MyAbstract {
        final int x;
        final byte y;

        public MyValue2(int x, byte y) {
            this.x = x;
            this.y = y;
        }
    }

    public static final primitive class MyValue1 extends MyAbstract {
        final int x;
        final long y;
        final short z;
        final Integer o;
        final MyValue2 v1;
        final MyValue2 v2;

        public MyValue1(int x, long y, short z, Integer o, MyValue2 v1, MyValue2 v2) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.o = o;
            this.v1 = v1;
            this.v2 = v2;
        }

        static MyValue1 setX(MyValue1 v, int x) {
            return new MyValue1(x, v.y, v.z, v.o, v.v1, v.v2);
        }
    }

    private static final MyValue1 testValue1 = MyValue1.default;

    MyValue1 valueField1 = testValue1;

    public Object test9() {
        MyValue1 /*Object*/ o = valueField1;
        for (int i = 1; i < 100; i *= 2) {
            //MyValue1 v = (MyValue1)o;
            o = MyValue1.setX(o, o.x + 1);
        }
        return o;
    }

}
