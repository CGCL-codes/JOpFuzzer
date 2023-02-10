/*
 * @test
 * @modules jdk.incubator.vector
 * @run testng/othervm -Xcomp -XX:CompileCommand=compileonly,TestCast16BTo2D::* -XX:+UnlockDiagnosticVMOptions -XX:+PrintIntrinsics TestCast16BTo2D
 */

import jdk.incubator.vector.*;

public class TestCast16BTo2D {
    static final VectorSpecies<Byte> SPECIESb = ByteVector.SPECIES_128;
    static final VectorSpecies<Double> SPECIESs = DoubleVector.SPECIES_128;

    static final int INVOC_COUNT = 5000;
    static final int size = 2;

    static byte[] ab = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    static double[] ad = new double[size];

    public static void testVectorCastB2D(byte[] input, double[] output) {
        ByteVector av = ByteVector.fromArray(SPECIESb, input, 0);
        DoubleVector bv = (DoubleVector) av.castShape(SPECIESs, 0);
        bv.intoArray(output, 0);
    }

    public static void main(String[] args) {
        for (int i = 0; i < INVOC_COUNT; i++) {
            testVectorCastB2D(ab, ad);
        }
    }
}
