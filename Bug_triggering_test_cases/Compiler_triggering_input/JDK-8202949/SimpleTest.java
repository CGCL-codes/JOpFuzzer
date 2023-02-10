public class SimpleTest {

    public static long instanceCount = 0;
    public static volatile double dFld = 0;

    public static void test() {
        int iArr[] = new int[10];

        for (int i = 6; i < 100; i++) {
            for (int j = 8; j > i; j--) {
                int k = 1;
                do {
                    iArr[j] -= k;
                    switch (((k % 5) * 5) + 74) {
                    case 91:
                        dFld += k;
                        break;
                    case 78:
                        instanceCount += k;
                        break;
                    }
                } while (++k < 1);
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; ++i) {
            test();
        }
    }
}
