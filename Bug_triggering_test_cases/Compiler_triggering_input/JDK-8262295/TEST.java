package test.misc;

public class TEST {

    public static Object escape1;
    public static Object escape2;

    public static void main(String[] args_ignored) {
        try {
            Object[] arrNotEmpty = {null, null, null, null, null, };

            // Warm-up
            for (int i = 50_000; i > 0; i--) {
                testMethod_dontinline(arrNotEmpty);
            }
            // Call testmethod with empty array often enough to trigger GC.
            // GC is assumed to crash.
            for (int i = 20_000_000; i > 0; i--) {
                // Trick for ParallelGC: empty[4] will be loaded in the testmethod
                // (out of bounds!) and interpreted as oop (or
                // narrowOop). PSScavenge::should_scavenge() will skip the loaded
                // value if it is before the young generation. So before calling the
                // test method we allocate the empty array and an array of -1 values
                // right behind it. So empty[4] will likely result in
                // 0xffffffffffffffff Which is not before the young generation.
                Object[] empty = new Object[0];
                long[] l = new long[4];
                l[0] = -1L; l[1] = -1L; l[2] = -1L; l[3] = -1L;
                escape2 = l;
                testMethod_dontinline(empty);
            }
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    public static void testMethod_dontinline(Object[] src) throws Exception {
        Object[] clone = src.clone();
        // Load L below is executed speculatively at this point from src without range check.
        // The result is put into the OopMap of the allocation in the next line.
        // If src.length is 0 then the loaded value is no heap reference and GC crashes.
        escape1 = new Object();
        if (src.length > 4) {
            escape2 = clone[4]; // Load L
        }
    }
}
