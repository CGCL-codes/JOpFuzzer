

public class Test {

    static boolean test23FailInit = true;

    static primitive class Test23ClassA {
        int x = 0;
        Test23ClassB b = Test23ClassB.default;
    }

    static primitive class Test23ClassB {
        int x = 0;
        static {
            if (test23FailInit) {
                throw new RuntimeException();
            }
        }
    }

    static public Object test23() {
        return Test23ClassA.default.b.x;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; ++i) {
            // Trigger initialization error in Test23ClassB
            try {
                Test23ClassB b = new Test23ClassB();
                throw new RuntimeException("Should have thrown error during initialization");
            } catch (ExceptionInInitializerError | NoClassDefFoundError e) {
                // Expected
            }
            try {
                test23();
                throw new RuntimeException("Should have thrown NoClassDefFoundError");
            } catch (NoClassDefFoundError e) {
                // Expected
            }
        }
    }
    
}
