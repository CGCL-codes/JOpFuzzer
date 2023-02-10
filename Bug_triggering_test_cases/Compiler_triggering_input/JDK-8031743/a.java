public class a {
    static int x;
    static long foo() {
        return x & 0xfffffffe;
    }

    public static void main(String[] args) {
        x = -1;
        long l = 0;
        for (int i = 0; i < 100000; ++i) {
            l = foo();
        }
        System.out.println(l);
    }
}