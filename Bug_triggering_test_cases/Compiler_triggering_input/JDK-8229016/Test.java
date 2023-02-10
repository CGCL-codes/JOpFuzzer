
public class Test {
    private static boolean b = false;

    private static void test() {
        Object[] array = {1, 2};
        System.arraycopy(array, 1, array, 0, array.length - 1);
        if (b) {
            // Uncommon trap
            System.out.println(array[0]);
        }
    }

    public static void main(String[] args) {
        // Trigger compilation
        for (int i = 0; i < 20_000; ++i) {
            test();
        }
    }
}
