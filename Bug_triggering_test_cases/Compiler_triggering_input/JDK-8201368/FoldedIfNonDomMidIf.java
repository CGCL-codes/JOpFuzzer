public class FoldedIfNonDomMidIf {
    public static void main(String[] args) {
        for (int i = 0; i < 20_000; i++) {
            test_helper(0, 0);
            test_helper(20, 0);
            test(12);
        }
        if (test(14) != null) {
            throw new RuntimeException("Incorrect code execution");
        }
    }

    private static Object test(int i) {
        return test_helper(i, 0x42);
    }

    static class A {

    }

    static final MyException myex = new MyException();

    private static Object test_helper(int i, int j) {
        Object res = null;
        try {
            if (i < 10) {
                throw myex;
            }

            if (i == 14) {

            }

            if (i > 15) {
                throw myex;
            }
        } catch (MyException e) {
            if (j == 0x42) {
                res = new A();
            }
        }
        return res;
    }

    private static class MyException extends Exception {
    }
}
