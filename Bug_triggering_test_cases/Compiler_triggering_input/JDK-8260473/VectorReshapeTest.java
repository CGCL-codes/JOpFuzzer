import jdk.incubator.vector.*;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;
import jdk.incubator.vector.VectorShape;
import jdk.incubator.vector.VectorSpecies;
import jdk.internal.vm.annotation.ForceInline;


/*
 Compile with:
  javac  --add-modules jdk.incubator.vector  \
         --add-exports java.base/jdk.internal.vm.annotation=ALL-UNNAMED \
         VectorReshapeTest.java \

 Run with:
  java  \
 -Xmx256m \
 -XX:-TieredCompilation \
 -XX:PrintIdealGraphLevel=3 \
 -XX:+PrintIdealGraph \
 -XX:PrintIdealGraphFile=igv.xml \
 -XX:LogFile=hotspot.log \
 "-XX:CompileCommand=compileonly,jdk/incubator/vector/ByteVector.fromByteBuffer" \
 -XX:CICompilerCount=1 \
 -XX:+UseZGC \
 -Xbatch \
 --add-modules jdk.incubator.vector \
 VectorReshapeTest
 */
public class VectorReshapeTest {
    static final int INVOC_COUNT = Integer.getInteger("jdk.incubator.vector.test.loop-iterations", 100);
    static final int NUM_ITER = 200 * INVOC_COUNT;

    static final VectorSpecies<Integer> ispec128 = IntVector.SPECIES_128;
    static final VectorSpecies<Float> fspec128 = FloatVector.SPECIES_128;
    static final VectorSpecies<Long> lspec128 = LongVector.SPECIES_128;
    static final VectorSpecies<Double> dspec128 = DoubleVector.SPECIES_128;
    static final VectorSpecies<Byte> bspec128 = ByteVector.SPECIES_128;
    static final VectorSpecies<Short> sspec128 = ShortVector.SPECIES_128;

    public static void main(String[] args) {
        IntFunction<byte[]> makeArray = size->{
            byte[] array = new byte[size];
            for(int x = 0; x < size; x++) {
                array[x] = (byte) x;
            }
            return array;
        };
         
        testRebracket128(makeArray);
    }

    static
    void checkPartialResult(VectorSpecies<?> a, VectorSpecies<?> b,
                            byte[] input, byte[] output, byte[] expected,
                            int part, int origin) {
        if (Arrays.equals(expected, output)) {
            return;
        }
        int block;
        block = Math.min(a.vectorByteSize(), b.vectorByteSize());

        System.out.println("input:  "+Arrays.toString(input));
        System.out.println("Failing with "+a+"->"+b+
                           " (reinterpret)"+
                           ", block=" + block +
                           ", part=" + part +
                           ", origin=" + origin);
        System.out.println("expect: "+Arrays.toString(expected));
        System.out.println("output: "+Arrays.toString(output));
        // Assert.assertEquals(expected, output);
        assert(expected.equals(output)); // SRDM
        try {
            Thread.sleep( 0);
        }catch(Exception e) {}
        Thread.dumpStack();
        System.exit(1);
    }
    

    @ForceInline
    static <E,F>
    void testVectorRebracket(VectorSpecies<E> a, VectorSpecies<F> b, byte[] input, byte[] output) {
        Vector<E> av = a.fromByteArray(input, 0, ByteOrder.nativeOrder());
        int block;
        assert(input.length == output.length);

        block = Math.min(a.vectorByteSize(), b.vectorByteSize());
        if (false)
            System.out.println("testing "+a+"->"+b+
                    (false?" (lanewise)":" (reinterpret)")+
                    ", block=" + block);
        byte[] expected;
        int origin;

        int part = 0;
        Vector<F> bv = av.reinterpretShape(b, part);
        bv.intoByteArray(output, 0, ByteOrder.nativeOrder());
        // in-place copy, no resize
        expected = input;
        origin = 0;
        checkPartialResult(a, b, input, output, expected,
                part, origin);

    }

    // TODO Auto-generated method stub
    static void testRebracket128(IntFunction<byte[]> fa) {
        byte[] barr = fa.apply(128/Byte.SIZE);
        byte[] bout = new byte[barr.length];
        for (int i = 0; i < NUM_ITER; i++) {
            testVectorRebracket(bspec128, bspec128, barr, bout);
            testVectorRebracket(bspec128, sspec128, barr, bout);
            testVectorRebracket(bspec128, ispec128, barr, bout);
        }
    }

}
