import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)

public class TestVect {
  static final int LENGTH = 1024 * 256;
  static short [] sa = new short[LENGTH];
  static short [] sb = new short[LENGTH];

  public static void testVectInit() {
    for (int i = 0; i < LENGTH; i++) {
       sa[i] = (short)(i + 3);
       sb[i] = (short)(i + 1);
    }
  }

  @Setup
  public void setup()
  {
    testVectInit();
  }

  @Benchmark
  public void testVectShift() {
    for (int i = 0; i < LENGTH; i++) {
      sb[i] = (short)(sa[i] >> 2);
    }
  }

}
