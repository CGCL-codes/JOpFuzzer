package org.openjdk.bench.jdk.incubator.foreign;

import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.MemoryAccess;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;
import jdk.incubator.vector.ByteVector;
import jdk.incubator.vector.IntVector;
import jdk.incubator.vector.VectorSpecies;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.CompilerControl;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 5, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@State(org.openjdk.jmh.annotations.Scope.Thread)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 1, jvmArgsAppend = {
        "--add-modules=jdk.incubator.foreign",
        "-Dforeign.restricted=permit",
        "--enable-native-access", "ALL-UNNAMED"})
public class TestLoadBytes {
    @Param("1024")
    private int size;

    private byte[] srcArray;
    private ByteBuffer srcBufferNative;
    private MemorySegment srcSegmentImplicit;

    @Setup
    public void setup() {
        srcArray = new byte[size];
        for (int i = 0; i < srcArray.length; i++) {
            srcArray[i] = (byte) i;
        }

        srcBufferNative = ByteBuffer.allocateDirect(size);
        srcSegmentImplicit = MemorySegment.allocateNative(size, ResourceScope.newImplicitScope());
    }

    @Benchmark
    public int arrayScalar() {
        int size = 0;
        for (int i = 0; i < srcArray.length; i ++) {
            var v = srcArray[i];
            size += v;
        }
        return size;
    }

    @Benchmark
    public int arrayScalarConst() {
        int size = 0;
        for (int i = 0; i < 1024; i ++) {
            var v = srcArray[i];
            size += v;
        }
        return size;
    }

    @Benchmark
    public int bufferNativeScalar() {
        int size = 0;
        for (int i = 0; i < srcArray.length; i++) {
            var v = srcBufferNative.get(i);
            size += v;
        }
        return size;
    }

    @Benchmark
    public int bufferNativeScalarConst() {
        int size = 0;
        for (int i = 0; i < 1024; i++) {
            var v = srcBufferNative.get(i);
            size += v;
        }
        return size;
    }

    @Benchmark
    public int segmentNativeScalar() {
        int size = 0;
        for (int i = 0; i < srcArray.length; i++) {
            var v = MemoryAccess.getByteAtOffset(srcSegmentImplicit, i);
            size += v;
        }
        return size;
    }

    @Benchmark
    public int segmentNativeScalarConst() {
        int size = 0;
        for (int i = 0; i < 1024; i++) {
            var v = MemoryAccess.getByteAtOffset(srcSegmentImplicit, i);
            size += v;
        }
        return size;
    }
}
