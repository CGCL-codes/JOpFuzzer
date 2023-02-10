class Reduced {
    static int N = 400;
    static long instanceCount;

    public static void main(String[] strArr) {
        for (int i = 0; i < 10 ; i++) {
            mainTest();
        }
    }

    static void mainTest() {
        vMeth();
    }

    static void vMeth() {
        int i17 = 89, i18, i19 = 44, i20 = 2, iArr3[] = new int[N];
        long l2, lArr[] = new long[N];
        byte by = 22;
        FuzzerUtils.init(iArr3, 131);
        for (i18 = 2; i18 < 350; ++i18) {
            i17 /= 8;
            for (l2 = 5; l2 > i18; l2 -= 2) {
                switch ((i18 % 3)) {
                    case 8:
                        iArr3[i18] |= i17;
                        break;
                    case 9:
                        i20 += (((l2) + by) - i19);
                        i19 += instanceCount;
                    case 10:
                }
            }
        }
    }
}

