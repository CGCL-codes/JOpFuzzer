class MyClass {
    int x = 42;
}

class MainClass {
    static int f1;

    static int test(MyClass obj1, MyClass obj2) {
        for (int i = 0; i < 60; ++i) {
            for (int j = 0; j < 10; ++j) {
                f1 = obj1.x;
                obj2.x = 12;
            }
        }
        return f1;
    }

    public static void main(String[] args) {
        MyClass obj = new MyClass() ;
        for (int i = 0; i < 10_000; ++i) {
            test(obj, obj);
        }
    }
}