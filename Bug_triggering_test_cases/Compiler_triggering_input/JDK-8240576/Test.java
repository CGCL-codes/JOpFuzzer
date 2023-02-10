public class Test {

    private int mI = 0;
    private long mJ = 0;
    private float mF = 0f;

    public void testMethod() {
        for (int i0 = 0; i0 < 100; i0++) {
            if (mF != 0) {
                // do nothing
            } else {
                try {
                    mJ = Long.MAX_VALUE;
                    for (int i1 = 0; i1 < 101; i1++) {
                        for (int i2 = 0; i2 < 102; i2++) {
                            mI = new Integer(0x1234);
                        }
                    }
                } catch (Exception ignored) {}
            }
        }
    }

    public static void main(String[] args) {
        Test t = new Test();
        t.testMethod();
    }
}