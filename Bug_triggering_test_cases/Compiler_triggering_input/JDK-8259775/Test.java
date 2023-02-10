import jdk.incubator.vector.ByteVector;
import jdk.incubator.vector.VectorSpecies;

import java.util.Arrays;

public class Test {

    static final VectorSpecies<Byte> SPECIES_256 = ByteVector.SPECIES_256;
    static final VectorSpecies<Byte> SPECIES_128 = ByteVector.SPECIES_128;

    static byte[] a = new byte[32];
    static byte[] b = new byte[32];

    private static void func() {
        ByteVector av = ByteVector.fromArray(SPECIES_256, a, 0);
        ByteVector bv = (ByteVector)av.reinterpretShape(SPECIES_128, 0).reinterpretShape(SPECIES_256, 0);
        bv.intoArray(b, 0);
    }

    public static void main(String[] args) {
        for (int i = 0; i < a.length; i++) {
            a[i] = (byte)i;
        }
        for (int i = 0; i < 100000; i++) {
            func();
        }
        System.out.println("a: " + Arrays.toString(a));
        System.out.println("b: " + Arrays.toString(b));
    }
}