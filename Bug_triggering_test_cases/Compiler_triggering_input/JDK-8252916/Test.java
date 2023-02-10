public class Test {

    static class MyClass {
        Object o1 = null;
        Object o2 = new Integer(42);
    }

    static Object test(boolean trap) {
        MyClass obj = new MyClass();
        if (trap) { }
        return obj.o1;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100_000; ++i) {
            test(false);
        }
    }
}