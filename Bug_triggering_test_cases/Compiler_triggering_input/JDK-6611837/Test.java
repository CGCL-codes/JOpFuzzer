public class Test {
    static final int arrsz = 64;
    private static int[] tbuf = new int[arrsz];
    private static int[] tbuf2 = new int[arrsz];

    private static void test(int iter) {
        for (int i = 0; i < iter; i++) {
            System.arraycopy(tbuf, 0, tbuf2, 0, 32);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < arrsz; i++) {
            tbuf[i] = i;
        }
        for (int i = 0; i < 5; i++) {
            test(10000);
        }
    }
}
