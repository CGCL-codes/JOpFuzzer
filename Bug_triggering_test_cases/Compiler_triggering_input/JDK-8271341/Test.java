public class Test {
    static long x;
    static int d;
    static float e;
    static long f;

    public static void main(String[] strArr) {
        for (int i = 0; i < 10; i++) {
            test();
        }
    }

    static void test() {
        int h = 0, i, j, k = 120, l, m, iArr[] = new int[400];
        short s = 909;
        boolean b = true;
        byte by = 9;
        for (int g = 9; 341 > g; g++) {
        }
        for (i = 2; i < 355; i += 2) {
            for (j = 1; j < 9; j++) {
                d = 6;
                h = 6;
                h += i + x;
                b = b;
                iArr[j] = s;
            }
        }
        for (l = 20; l < 393; l++) {
            for (m = 1; m < 5; m += 2) {
                e = by;
                try {
                    d = k % l;
                } catch (ArithmeticException p) {
                }
            }
        }
        f = h + i + checkSum(iArr);
    }

    public static long checkSum(int[] a) {
        long sum = 0;
        for (int j = 0; j < a.length; j++) {
            sum += (a[j] / (j + 1) + a[j] % (j + 1));
        }
        return sum;
    }
}


