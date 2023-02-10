
public class uu {
  public static void main(String[] args) throws Throwable {
    final int n = 10000;

    // sleep for 1ms
    final long[] time = new long[n + 1];
    for (int i = 0; i < time.length; i++) {
        time[i] = System.nanoTime();
        Thread.sleep(1);
    }

    // create sorted time deltas
    final long[] dt = new long[time.length - 1];
    for (int i = 0; i < dt.length; i++) {
        dt[i] = time[i + 1] - time[i];
    }
    Arrays.sort(dt);

    // print percentiles
    final double nsToMs = 1E-6;
    System.out.println(String.format("10    : %.1f ms", dt[(int) (dt.length * 0.100)] * nsToMs));
    System.out.println(String.format("30    : %.1f ms", dt[(int) (dt.length * 0.300)] * nsToMs));
    System.out.println(String.format("50    : %.1f ms", dt[(int) (dt.length * 0.500)] * nsToMs));
    System.out.println(String.format("70    : %.1f ms", dt[(int) (dt.length * 0.700)] * nsToMs));
    System.out.println(String.format("90    : %.1f ms", dt[(int) (dt.length * 0.900)] * nsToMs));
    System.out.println(String.format("95    : %.1f ms", dt[(int) (dt.length * 0.950)] * nsToMs));
    System.out.println(String.format("99    : %.1f ms", dt[(int) (dt.length * 0.990)] * nsToMs));
    System.out.println(String.format("99.9  : %.1f ms", dt[(int) (dt.length * 0.999)] * nsToMs));
    System.out.println(String.format("99.99 : %.1f ms", dt[(int) (dt.length * 0.9999)] * nsToMs));
    System.out.println(String.format("Total runtime: %.1f s", (time[n] - time[0]) * 1E-9));

  }
}
