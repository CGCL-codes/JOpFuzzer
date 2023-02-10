import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class DefFormatterBench {

    @Param({ "1.23", "1.49", "1.80", "1.7", "0.0", "-1.49", "-1.50", "9999.9123", "1.494", "1.495", "1.03", "25.996",
            "-25.996" })
    public double value;

    private DefNumerFormat dnf = new DefNumerFormat();

    @Benchmark
    public void testDefNumberFormatter(final Blackhole blackhole) {
        blackhole.consume(this.dnf.format(this.value));
    }

    public static void main(String... args) throws Exception {

        Options opts = new OptionsBuilder().include(DefFormatterBench.class.getSimpleName()).shouldDoGC(true).build();

        new Runner(opts).run();
    }

    private static class DefNumerFormat {
        private final NumberFormat n;

        public DefNumerFormat() {
            this.n = NumberFormat.getInstance(Locale.ENGLISH);
            this.n.setMaximumFractionDigits(2);
            this.n.setMinimumFractionDigits(2);
            this.n.setGroupingUsed(false);
        }

        public String format(final double d) {
            return this.n.format(d);
        }
    }
}