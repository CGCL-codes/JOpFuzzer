public class TestBeautifyLoops_2 {

    private class X {
        public int x() {
            return -1;
        }
    }

    private int mI = 0;
    private float mF = 0;
    private boolean mZ = false;
    private X mX = new X();
    private long[] mArray = new long[331];

    private void testMethod() {
        double d = 0;

        for (int i = 0; i < 331; i++) {
            if (mZ) {
                continue;
            }

            try {
                for (int j = mArray.length - 1; j >= 0; j--) {
                    {
                        for (int k = 0; k < 331; k++) {
                            d += ((double) new Double(d));
                            switch (267) {
                                case 256:
                                default: {
                                    mF += (mX.x());
                                    break;
                                }
                            }
                        }
                    }
                }
            } catch (Exception ignore) {
            }
        }
    }

    public static void main(String[] args) {
        TestBeautifyLoops_2 obj = new TestBeautifyLoops_2();
        obj.testMethod();
    }

}