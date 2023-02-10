class MyValue {
    int b = 2;
}

class MainClass {

    int iField;
    MyValue c;
    MyValue t;

    void test(MyValue[] array) {
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
                iField = 6;
            }
            for (int j = 0; j < 2; ++j) {
                iField += array[0].b;
            }
            MyValue[] array2 = {new MyValue()};
            c = array[0];
            array2[0] = t;
        }
    }

    public static void main(String[] args) {
        MainClass q = new MainClass();
        MyValue[] array = {new MyValue()};
        for (int i = 0; i < 50_000; ++i) {
            q.test(array);
        }
    }
}