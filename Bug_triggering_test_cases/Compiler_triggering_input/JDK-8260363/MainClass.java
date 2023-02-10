class Test0 {
    int x = 42;
    short[] array = new short[7];
}

public class MainClass {
    int f = 1;

    public void method1(Test0[] array) {
        for (int i = 0; i < 100; ++i) {
            array[0] = array[0];
            for (int j = 0; j < 10; ++j) {
                for (int k = 0; k < 10; ++k) {
                    f = 42;
                }
            }
        }
    }

    public static void main(String[] args) {
        MainClass t = new MainClass();
        Test0[] array = new Test0[] {new Test0()};
        for (int l1 = 0; l1 < 10000; ++l1) {
            t.method1(array);
        }
    }
}