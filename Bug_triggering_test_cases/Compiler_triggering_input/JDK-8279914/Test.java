public class Test {
    static void print(int a) {
        System.out.println(a);
    }

    static void foo(int x, long y) {
        int z = x | (int)y;

        if (z > 0) {
            print(z);
        }
    }

    public static void main(String[] args) {
        int x = 7;
        long y = 21474836472L;

        for (int i = 0; i < 20000; i++) {
            foo(x, y);
        }
    }
}