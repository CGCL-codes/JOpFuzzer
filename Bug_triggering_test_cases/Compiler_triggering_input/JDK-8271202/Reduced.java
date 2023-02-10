public class Reduced {
    public static void main(String[] strArr) {
        try {
            test();
        } catch (Exception e) {
            // Expected
        }
    }

    static void test() {
        long l6 = 10L;
        int counter = 0;
        int i2, i26, i29, iArr[] = new int[400];
        boolean b3 = true;
        for (int smallinvoc = 0; smallinvoc < 139; smallinvoc++) {
        }
        for (i2 = 13; i2 < 1000; i2++) {
            for (i26 = 2; i26 < 114; l6 += 2) {
                // Infinite loop
                if (b3) {
                    for (i29 = 1; i29 < 2; i29++) {
                        try {
                            iArr[i26] = 0;
                        } catch (ArithmeticException a_e) {
                        }
                    }
                }
                counter++;
                if (counter == 100000) {
                    throw new RuntimeException("expected");
                }
            }
        }
    }
}
