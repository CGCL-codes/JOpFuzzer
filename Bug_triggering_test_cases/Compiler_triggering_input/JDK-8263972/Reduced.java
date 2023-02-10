class Test {
    int a = 400;

    public static void main(String[] g) {
        Test t = new Test();
        for (int i = 0; i < 10; i++) {
            t.b();
        }
    }

    void b() {
        int i16, d = 5, e = -56973;
        long f[] = new long[a];
        FuzzerUtils.init(f, 5);
        for (i16 = 2; i16 < 92; i16++) {
            f[i16 - 1] *= d;
            f[i16 + 1] *= d;
        }
        while (++e < 0) {
        }
        FuzzerUtils.out.println("i21 i22 lArr = " + FuzzerUtils.checkSum(f));
    }
}

