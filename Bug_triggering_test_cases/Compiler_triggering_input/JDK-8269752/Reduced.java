class Reduced {
    static int N;
    static long vMeth_check_sum;

    public static void main(String[] strArr) {
        Reduced _instance = new Reduced();
        for (int i = 0; i < 10; i++) {
            _instance.test();
        }
    }

    void test() {
        vMeth(3);
    }

    void vMeth(int i2) {
        double d = 1.74287;
        int i3 = -36665, i4, iArr[] = new int[N];
        short s;
        long lArr[] = new long[N];
        while (++i3 < 132) {
            if (i2 != 0) {
                vMeth_check_sum += i3;
                return;
            }
            i4 = 1;
            while (++i4 < 12) {
                i2 += i4;
            }
        }
        vMeth_check_sum += i3;
    }
}
