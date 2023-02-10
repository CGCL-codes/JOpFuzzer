public class Nested {
    public static int value = 17;

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 100; ++i) {
            int result = runTest();
            System.out.println(result);
        }
    }

    public static int runTest() {
        int sum = 0;
        for (int j = 0; j < 100000; j = java.lang.Math.addExact(j, 1)) {
            sum = 1;
            for (int i = 0; i < 5; ++i) {
                sum = sum * value; //* value;
            }
        }
        return sum;
    }
}