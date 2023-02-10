import jdk.incubator.vector.*;

public class TestLoopStoreVector {
    static final VectorSpecies<Integer> SPECIESi = IntVector.SPECIES_PREFERRED;
    static final VectorSpecies<Long> SPECIESl = LongVector.SPECIES_PREFERRED;

    static final int INVOC_COUNT = 5000;
    static final int size = 64;

    static int[] ai = {21, 22, 23, 24, 25, 26, 27, 28, 29, 210, 211, 212,
            213, 214, 215, 216, 217, 218, 219, 220, 221, 222, 223, 224,
            225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 235, 236,
            237, 238, 239, 240, 241, 242, 243, 244, 245, 246, 247, 248,
            249, 250, 251, 252, 253, 254, 255, 256, 257, 258, 259, 260,
            261, 262, 263, 264};
    static long[] al = {41, 42, 43, 44, 45, 46, 47, 48, 49, 410, 411, 412,
            413, 414, 415, 416, 417, 418, 419, 420, 421, 422, 423, 424,
            425, 426, 427, 428, 429, 430, 431, 432, 433, 434, 435, 436,
            437, 438, 439, 440, 441, 442, 443, 444, 445, 446, 447, 448,
            449, 450, 451, 452, 453, 454, 455, 456, 457, 458, 459, 460,
            461, 462, 463, 464};

    public static void testVectorCastL2I(long[] input, int[] output, VectorSpecies<Long> speciesl, VectorSpecies<Integer> speciesi) {
        LongVector av = LongVector.fromArray(speciesl, al, 0);
        IntVector bv = (IntVector) av.castShape(speciesi, 0);
        bv.intoArray(output, 0);
    }

    public static int test0() {
        for (int i = 0; i < 1000; i++) {
            testVectorCastL2I(al, ai, SPECIESl, SPECIESi);
        }
	return 0;
    }

    public static void main(String[] args) {
        for (int i = 0; i < INVOC_COUNT; i++) {
            test0();
        }
	for(int  i= 0; i < 64; i++) {
	    System.out.println(ai[i]+" ");
	}
    }
}
