class Test2 {
    static int N = 400;
    static double dArrFld[] = new double[N];
    static long iMeth_check_sum = 0;

    static {
        init(dArrFld, 90.71133);
    }

    float fArrFld[] = new float[N];

    public static void main(String[] strArr) {
        Test2 _instance = new Test2();
        for (int i = 0; i < 10; i++) {
            _instance.mainTest();
        }
    }

    void mainTest() {
        int i24 = 121110, i28, i30;
        float f2 = 2.486F;

        for (i28 = 322; i28 > 6; i28--) {
            i30 = 1;
            do {
                i24 = (int) f2;
                fArrFld[1] += i30;
                switch (((i28 % 4) * 5) + 32) {
                    case 36:
                        f2 *= f2;
                }
            } while (++i30 < 80);
        }
        System.out.println(i24 + ",");
    }

    public static void init(double[] a, double seed) {
        for (int j = 0; j < a.length; j++) {
            a[j] = (j % 2 == 0) ? seed + j : seed - j;
        }
    }
}
