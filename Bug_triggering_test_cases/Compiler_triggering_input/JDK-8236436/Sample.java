public class Sample {

    public static void main(String[] args) {
        System.out.println("Start");
        test(Test4::simpleFunction, "Round 1");
        test(Test4::simpleFunction, "Round 2");
        test(Test4::simpleFunction, "Round 3");
        test(Test4::simpleFunction, "Round 4");
        test(Test4::simpleFunction, "Round 5");
        test(Test4::simpleFunction, "Round 6");
        System.out.println("Done");
    }

    /**
     * Function that invokes a given function pointer in a loop.
     *
     * @param testFunc Function pointer to invoke
     * @param name prefix to print
     */
    private static void test(Function<?, ?> testFunc, String name) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1_000_000_000; i++) {
            testFunc.apply(null);
        }
        long endTime = System.currentTimeMillis();
        System.out.println(name + ": " + (endTime - startTime) + " ms");
    }

    private static Object simpleFunction(Object o) {
        return null;
    }
}