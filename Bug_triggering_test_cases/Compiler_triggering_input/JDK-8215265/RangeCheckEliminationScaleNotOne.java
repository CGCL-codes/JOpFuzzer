import java.util.Arrays;

public class RangeCheckEliminationScaleNotOne {
    public static void main(String[] args) {
        {
            int[] array = new int[199];
            boolean[] flags = new boolean[100];
            Arrays.fill(flags, true);
            flags[0] = false;
            flags[1] = false;
            for (int i = 0; i < 20_000; i++) {
                test1(100, array, 0, flags);
            }
            boolean ex = false;
            try {
                test1(100, array, -5, flags);
            } catch (ArrayIndexOutOfBoundsException aie) {
                ex = true;
            }
            if (!ex) {
                throw new RuntimeException("no AIOOB exception");
            }
        }
    }

    private static int test1(int stop, int[] array, int offset, boolean[] flags) {
        if (array == null) {}
        int res = 0;
        for (int i = 0; i < stop; i++) {
            if (flags[i]) {
                res += array[2 * i + offset];
            }
        }
        return res;
    }
}