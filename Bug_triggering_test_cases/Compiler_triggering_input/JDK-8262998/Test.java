import jdk.incubator.vector.*;
import java.nio.ByteOrder;

public class Test {
    static final VectorSpecies<Double> SPECIES256 = DoubleVector.SPECIES_256;
    static byte[] a = new byte[512];
    static byte[] r = new byte[512];

    static void test() {
        DoubleVector av = DoubleVector.fromByteArray(SPECIES256, a, 0, ByteOrder.BIG_ENDIAN);
        av.intoByteArray(r, 0, ByteOrder.BIG_ENDIAN);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            test();
        }
        System.out.println(r[0]);
    }
}