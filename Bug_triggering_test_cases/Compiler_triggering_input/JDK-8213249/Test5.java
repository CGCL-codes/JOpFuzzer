public class Test5 {

    public static int testAIOOBESnippet(int[] array) {
        return array[10];
    }

    public void testAIOOBE() {
        int[] array = new int[4];
        for (int i = 0; i < 10000; i++) {
            try {
                testAIOOBESnippet(array);
            } catch (ArrayIndexOutOfBoundsException e) {
                if (e.getMessage() == null) {
                  throw new RuntimeException("FAIL");
                }
            }
        }
    }

    public static void main(String[] args) {
        Test5 test = new Test5();
        for (int i = 0; i < 100_000; ++i) {
            test.testAIOOBE();
        }
    }
}
