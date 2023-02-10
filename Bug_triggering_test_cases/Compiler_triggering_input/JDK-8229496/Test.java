public class Test {

    public static int test(int div, int array[]) {
        int res = 0;
        for (int i = 0; i < 256; i++) {
            int j = 0;
            do {
                array[i] = i;
                try {
                    res = 1 % div;
                } catch (ArithmeticException ex) { }
            } while (++j < 9);
        }
        return res;
    }

    public static void main(String[] args) {
        int array[] = new int[256];
        for (int i = 0; i < 100_000; ++i) {
            test(0, array);
        }
    }
}
