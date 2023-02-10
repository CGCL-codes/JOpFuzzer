public class Test {

    public static void method() { }

    public static void test() {
        try {
            Object obj = new Object();
            for (long i = 0; i < 1000; ++i) {
                for (long j = 0; j < 1000; ++j) {
                
                }
                method();
            }
        } catch (Exception e) {
            // Infinite loop
            while (true) { }
        }
    }

    public static void main(String[] args) {
        test();
    }
}

