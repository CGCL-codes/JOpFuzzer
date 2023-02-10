public class SplitIfSharedFastLock {
    private static boolean field;
    private static A obj_field;

    public static void main(String[] args) {
        A lock = new A();
        for (int i = 0; i < 20_000; i++) {
            test(true, lock);
            test(false, lock);
        }
    }

    private static void test(boolean flag, Object obj) {
        if (obj == null) {
        }

        obj_field = (A)obj;

        boolean flag2;
        if (flag) {
            field = true;
            flag2 = true;
        } else {
            field = false;
            flag2 = false;
        }

        // This will become a single CastPP node
        if (flag) {
            obj = obj_field;
        }

        // This loop will be unswitched. The condition becomes candidate for split if
        for (int i = 0; i < 100; i++) {
            if (flag2) {
                field = true;
            } else {
                field = false;
            }
            synchronized (obj) {
                field = true;
            }
        }
    }

    private static final class A {
    }
}
