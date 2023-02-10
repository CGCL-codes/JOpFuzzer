import jdk.incubator.vector.*;

public class TestVectorPrintInline {
    static final VectorSpecies<Long> SPECIESl = LongVector.SPECIES_PREFERRED;

    static final int INVOC_COUNT = 5000;
    static final int size = 64;
    static long[] al = new long[size];

    static LongVector av = LongVector.fromArray(SPECIESl, al, 0);

    public static void main(String[] args) {
        LongVector av = LongVector.fromArray(SPECIESl, al, 0);
        av.intoArray(al, 0);
    }
}
