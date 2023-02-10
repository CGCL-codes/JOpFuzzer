// java -XX:-TieredCompilation -XX:-BackgroundCompilation -XX:-UseOnStackReplacement -XX:+PrintCompilation -XX:PrintIdealGraphFile=graph.xml -XX:PrintIdealGraphLevel=2 -XX:CompileOnly=AntiDependentLoadInOuterStripMinedLoop::test -XX:LoopMaxUnroll=0 AntiDependentLoadInOuterStripMinedLoop

public class AntiDependentLoadInOuterStripMinedLoop {
    private static int field;
    private static int field2;
    private static volatile int barrier;

    public static void main(String[] args) {
        int[] array = new int[1];
        for (int i = 0; i < 20_000; i++) {
            test(array);
        }
    }

    private static int test(int[] array) {
        int res = 1;

        for (int i = 0; i < 10; i++) {
            barrier = 1;

            for (int j = 0; j < 2000; j++) {
                array[0] = j;
                res *= j;
            }
        }

        return field + res + field * 2;
    }
}
