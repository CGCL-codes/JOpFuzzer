public class TestReplaceEquivPhis {

    public static final int N = 400;
    public static volatile int instanceCount = 0;
    public int iFld = 0;
    public static int iArrFld[] = new int[N];

    public int test() {
        int v = 0;
        boolean bArr[] = new boolean[N];

        for (int i = 1; i < 344; i++) {
            iFld = i;
            for (int j = 2; j <177 ; j++) {
                v = iFld;
                iFld = TestReplaceEquivPhis.instanceCount;
                TestReplaceEquivPhis.iArrFld[i] = 0;
                iFld += TestReplaceEquivPhis.instanceCount;
                TestReplaceEquivPhis.iArrFld[i] = 0;
                bArr[j] = false;
                TestReplaceEquivPhis.instanceCount = 1;

                for (int k = 1; k < 3; k++) {
                    // do nothing
                }
            }
        }
        return v;
    }

    public static void main(String[] args) {
        TestReplaceEquivPhis obj = new TestReplaceEquivPhis();
        for (int i = 0; i < 5; i++) {
            int result = obj.test();
            System.out.println("v = " + result);
            //if (result != 2) {
            // throw new RuntimeException("Test failed.");
            //}
        }
        //System.out.println("Test passed.");
    }

}