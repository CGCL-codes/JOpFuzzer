/**
 * @test
 *
 * @run main/othervm -Xcomp -XX:CompileOnly=TestInfiniteLoopNotInnerMost::test TestInfiniteLoopNotInnerMost
 *
 */
public class TestInfiniteLoopNotInnerMost {
    public static void main(String[] args) {
        test(false);
    }

    private static void test(boolean flag) {
        if (flag) {
            while (true) {
                for (int i = 1; i < 100; i *= 2) {

                }
            }
        }
    }
}