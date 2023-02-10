// -Xcomp -XX:-TieredCompilation -XX:CompileCommand=quiet -XX:CompileCommand=compileonly,Test::test -XX:-UseCountedLoopSafepoints -XX:LoopUnrollLimit=200 Test

public class Test {
    public static int test(int[] array) {
        int result = 0;
        int[] copy = new int[8];
        for (int i = 0; i < array.length; i++) {
            for (int j = 5; j < i; j++) {
                copy[j] += array[j]; // Loop is over-unrolled and copy[j] becomes dead because j is out of bounds
                result += array[j];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        test(new int[8]);
    }
}
