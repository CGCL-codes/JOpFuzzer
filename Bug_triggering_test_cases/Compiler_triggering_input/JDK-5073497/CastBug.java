/**
 * Demonstrate intermittent problem casting long to int.
 */
public class CastBug {

    /** Double the size of internal arrays and repack. */
    public static void main(String[] args) {
        int i = 0;
        long[] t = new long[100];
        for (int j = 0; j < t.length; j++) {
            t[j] = 0x8000000200011624L + (long) j;
        }
        while (++i < 100000) {
            final int z = i % t.length;
            final long v = t[z];
            final long oldv = v;
            t[z] = 0L;
            if (v > 0) {
                ;
            } else {
                int secondaryIndex = (int) v;
                if (secondaryIndex == 0) {
                    System.err.println("Bad cast: " + secondaryIndex + " == (int) 0x" + Long.toHexString(v) + "L z was " + z);
                    return;
                }
                for (int j = 0; j < 2; j++) {
                    secondaryIndex = ~secondaryIndex;
                }
            }
            t[z] = oldv;
        }
    }

}