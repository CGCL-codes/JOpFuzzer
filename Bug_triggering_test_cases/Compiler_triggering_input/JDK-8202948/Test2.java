public class Test2 {
    public static final int N = 400;
    public static int iFld=10;
    public static int iArr[]=new int[N];

    public static void mainTest() {
        int i0=1, i2;
        while (++i0 < 339) {
            if ((i0 % 2) == 0) {
                for (i2 = 2; i2 > i0; i2 -= 3) {
                    iArr[i2 - 1] &= iFld;
                }
            }
        }
    }

    public static void main(String[] strArr) {
        try {
            for (int i = 0; i < 10; i++) {
                 mainTest();
            }
         } catch (Exception ex) {
            System.out.println(ex.getClass().getCanonicalName());
         }
    }
}
