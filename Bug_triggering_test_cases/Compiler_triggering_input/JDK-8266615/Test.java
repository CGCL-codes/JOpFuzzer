public class Test {

    static interface MyInterface { }

    static class MyClassA { }

    static class MyClassB extends MyClassA implements MyInterface { }

    static MyInterface[] getArray() {
        return new MyClassB[0];
    }

    static MyClassA[] test1() {
        return (MyClassA[])getArray();
    }

    static void test2() {
        if (!(getArray() instanceof MyClassA[])) {
            throw new RuntimeException("test2 failed");
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 50_000; ++i) {
            test1();
            test2();
        }
    }
}
