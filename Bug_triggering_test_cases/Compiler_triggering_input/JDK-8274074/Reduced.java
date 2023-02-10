public class Reduced {
    static int iFld = 1;
    static int x = 1;
    static int q = 0;
    static int iArrFld[] = new int[100];

    public static void main(String[] strArr) {
        test();
    }

    static void test() {
        int y = 1;
        int i = 1;
        do {
            int j;
            for (j = 1; j < 88; j++) {
                iArrFld[1] = x;
            }
            try {
                y = iFld - q;
                y = (iArrFld[2] / y);
                y = (5 / iFld);
            } catch (ArithmeticException a_e) {
            }

            iFld -= 8;
            if (y == 3) {
            }
            i++;
        } while (i < 10);
    }

}

