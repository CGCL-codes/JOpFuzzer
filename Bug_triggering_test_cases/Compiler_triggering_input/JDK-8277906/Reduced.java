public class Reduced {
    static int iFld;
    final int N = 400;

    public static void main(String[] strArr) {
        Reduced _instance = new Reduced();
        for (int i = 0; i < 10; i++) {
            _instance.test();
        }
    }

    void test() {
        long l1, l2, lArr[] = new long[N];
        int i19, i20, iArr3[] = new int[N];
        float f4 = 89.728F;
        for (i20 = 3; i20 < 174; i20 += 3) {
            for (l2 = 1; l2 < i20; ++l2) {
                iFld -= iArr3[(int)l2] = i20;
                f4 += l2;
            }
        }
        System.out.println("iArr3 = " + checkSum(iArr3));
    }

    public static long checkSum(int[] a) {
        long sum = 0;
        for (int j = 0; j < a.length; j++) {
            sum += (a[j] / (j + 1) + a[j] % (j + 1));
        }
        return sum;
    }
}
