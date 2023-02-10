public class test {
    public static int foo (int x) {
        return Integer.rotateLeft(x, 1);
    }

    public static void main(String[] args) {
        int x = 0x1234;

        for (int i = 0; i < 50000; i++) {
            x = foo(x);
        }
    }
}
