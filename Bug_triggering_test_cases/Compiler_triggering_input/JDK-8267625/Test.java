public class Test {
    interface I {
        int func();
    }

    static class A implements I {
        public int func() { return 42; }
    }

    public static int test(A o, int n) {
        return n * 27 + o.func();
    }
        
    public static void main(String[] args) {
        for (int i = 0; i < 1000000; i++)
            test(new A(), i);
    }
}
