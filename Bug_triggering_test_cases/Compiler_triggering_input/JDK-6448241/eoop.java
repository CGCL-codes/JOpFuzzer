/*
 * @test %W% %E%
 * @bug 6448241
 * @run main/othervm -Xcomp -Xverify:none eoop
 */

public class eoop {
    static class UnloadedThrowable extends RuntimeException {
        static boolean throwIt = true;
        static {
            if (throwIt)
                throw new InternalError("can't load this");
        }
    }

    public static void main(String[] args) {
        try {
            throw new InternalError("throw me");
        } catch (UnloadedThrowable e) {
        }
    }
}