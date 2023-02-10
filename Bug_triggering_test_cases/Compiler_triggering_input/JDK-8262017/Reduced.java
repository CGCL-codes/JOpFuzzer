class Reduced {
    static int a = 400;
    static volatile int b;
    static long lFld;

    public static void main(String[] k) {
        test();
    }

    public static void test() {
        int e, f, g, h[] = new int[a];
        double i[] = new double[a];
        long j = 9;
        Helper.init(h, 3);
        for (e = 5; e < 154; e++) {
            for (f = 1; f < 168; f += 2) {
                b = e;
            }
            i[1] = b;
            for (g = 8; 168 > g; g += 2) {
                for (j = g; j < 3; j++) {
                    switch (3) {
                        case 3:
                    }
                }
            }
        }
        lFld = j;
    }
}

class Helper {
    public static void init(int[] a, int seed) {
        for (int j = 0; j < a.length; j++) {
            a[j] = (j % 2 == 0) ? seed + j : seed - j;
        }
    }
}
