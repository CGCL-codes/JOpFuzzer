/**
 * @test
 *
 * @run main/othervm -XX:LoopUnrollLimit=0 -XX:-UseOnStackReplacement -XX:-BackgroundCompilation -XX:CompileCommand=dontinline,TestEliminatedLoadPinnedOnBackedge::notInlined
 * -XX:CompileCommand=inline,TestEliminatedLoadPinnedOnBackedge::inlined TestEliminatedLoadPinnedOnBackedge
 *
 */

public class TestEliminatedLoadPinnedOnBackedge {
    private static Object field2;

    final static int iters = 2000;

    public static void main(String[] args) {
        boolean[] flags = new boolean[iters];
        for (int i = 0; i < iters; i++) {
            flags[i] = i < iters/2;
        }
        for (int i = 0; i < 20_000; i++) {
            test1(flags);
            inlined(new Object(), 1);
            inlined(new Object(), 4);
            inlined2(42);
            inlined2(0x42);
        }
    }

    static int field;

    private static int test1(boolean[] flags) {
        int k = 2;
        for (; k < 4; k *= 2) {
        }
        int[] array = new int[10];
        notInlined(array);
        int v = array[0];
        array[1] = 42;
        Object o = new Object();
        inlined(o, k);
        int i = 0;
        for (; ; ) {
            synchronized (array) {
            }
            if (i >= iters) {
                break;
            }
            v = array[0];
            if (flags[i]) {
                inlined2(array[1]);
            }
            i++;
        }
        return v;
    }
}