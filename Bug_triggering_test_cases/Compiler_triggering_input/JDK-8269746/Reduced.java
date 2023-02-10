class Reduced {
    static long instanceCount;
    int N = 400;
    volatile int iFld;
    int iFld1;

    public static void main(String[] strArr) {
        Reduced _instance = new Reduced();
        for (int i = 0; i < 10; i++) {
            _instance.test(strArr);
        }
    }

    void test(String[] strArr1) {
        float f, f3, fArr[] = new float[N];
        int i11 = 52176, i12 = 7, i13 = 4, i14 = 33932, i15, i16, iArr2[] = new int[N];
        byte by = 97;
        boolean b2 = true;
        long lArr2[][] = new long[N][N];
        for (f3 = 7; f3 < 195; ++f3) {
            i12 = 1;
            while (++i12 < 133) {
                lArr2[(int) (f3 - 1)][(int) (f3 - 1)] *= iFld;
                for (i13 = i12; 1 > i13; ) {
                    boolean b1;
                    switch ((i12 % 1)) {
                        case 53:
                            lArr2[1][1] -= i14;
                    }
                }
                if (b2) {
                    iFld1 += (((i12 * i13) + instanceCount) - i14);
                    i11 *= by;
                }
            }
            for (i15 = (int) (f3); i15 < 133; i15++) {
                i11 -= i12;
                iArr2[(int) (f3)] -= i14;
                instanceCount += i15;
            }
        }
        System.out.println(i11 + "," + i12 + "," + i13);
    }
}
