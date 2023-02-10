public class Reduced {

    public static void main(String[] strArr) {
        for (int i = 0; i < 1000; i++) {
            vMeth();
        }
    }

    static int vMeth() {
        int i2 = 3, iArr1[] = new int[200];

        for (int i9 = 3; i9 < 100; i9++) {
            try {
                int i10 = (iArr1[i9 - 1]);
                i2 = (i10 / i9);
            } catch (ArithmeticException a_e) {
            }
        }
        return i2;
    }
}
