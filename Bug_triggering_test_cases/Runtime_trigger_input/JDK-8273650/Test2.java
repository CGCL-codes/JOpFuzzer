

public class Test2 {

    static boolean test25FailInit = true;

    static primitive class Test25ClassA {
        Test25ClassB b = Test25ClassB.default;
    }

    static primitive class Test25ClassB {
        static {
            if (test25FailInit) {
                throw new RuntimeException();
            }
        }
    }

    // Same as test24 but with empty ClassB
    public static Object test25() {
        return Test25ClassA.default.b;
    }

    public static void main(String[] args) {
        // Trigger initialization error in Test25ClassB
        try {
            Test25ClassB b = new Test25ClassB();
            throw new RuntimeException("Should have thrown error during initialization");
        } catch (ExceptionInInitializerError | NoClassDefFoundError e) {
            // Expected
        }
        for (int i = 0; i < 10; ++i) {
            try {
                test25();
                throw new RuntimeException("Should have thrown NoClassDefFoundError, iteration: " + i);
            } catch (NoClassDefFoundError e) {
                // Expected
            }
        }
    }

}
