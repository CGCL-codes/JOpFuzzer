public class Test {

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            test();
        }
    }

    public static void test() {
        bar();
    }
    
    private static String bar() {
        return null;
    }
}
