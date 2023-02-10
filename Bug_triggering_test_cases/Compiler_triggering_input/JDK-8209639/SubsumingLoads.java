public class SubsumingLoads {
    private static Object field;
    private static boolean do_throw;
    private static volatile boolean barrier;

    public static void main(String[] args) {
        for (int i = 0; i < 20_000; i++) {
            do_throw = true;
            field = null;
            test(0);
            do_throw = false;
            field = new Object();
            test(0);
        }
    }

    private static float test(float f) {
        Object v = null;
        try {
            not_inlined();
            v = field;
        } catch (MyException me) {
            v = field;
            barrier = true;
        }
        if (v == null) {
            return f * f;
        }
        return f;
    }

    private static void not_inlined() throws MyException{
        if (do_throw) {
            throw new MyException();
        }
    }

    private static class MyException extends Throwable {
    }
}
