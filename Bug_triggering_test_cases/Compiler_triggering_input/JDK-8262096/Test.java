import jdk.incubator.vector.*;
import java.nio.ByteOrder;

public class Test {
    static final VectorSpecies<Double> SPECIES = DoubleVector.SPECIES_64;
    static double[] a = new double[64];
    static double[] r = new double[64];

    static void test() {
    }

    public static void main(String[] args) {
        DoubleVector av = DoubleVector.fromArray(SPECIES, a, 0);
        av.lanewise(VectorOperators.ABS).intoArray(r, 0);
    }
}