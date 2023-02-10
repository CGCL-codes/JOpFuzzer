import java.text.DecimalFormat;
import java.text.MessageFormat;

/**
 * Benchmark for method devirtualization and inlining.
 *
 * This is technically a microbenchmark (tested code is minimal), but
 * we make a lot of effort to prevent "excessive" optimizations that
 * would make results irrelevant or hard to analyse. The techniques
 * are apparently very successfull for the current crop of JITs.
 *
 * (c) 2002 by Osvaldo Pinali Doederlein.
 * Released to public domain.
 */
abstract public class MethodBench
{
    /**
     * Start time for the benchmark cycle.
     */
    private static long start;

    /**
     * Total time for the benchmark cycle.
     */
    private static long span;

    /**
     * Divider for the benchmark cycle, used for timer tuning.
     */
    private static int divider;

    /**
     * Formatter for timings.
     */
    private static final DecimalFormat fmt = new DecimalFormat("00.000");

    /**
     * Declare the helper here, init later, to fight dataflow optimizations.
     */
    protected static HelperC helper;

    /**
     * Consumes a value produced by the benchmark, so its calculation cannot be
     * eliminated as dead code (but it might still be computed in compilation time).
     */
    protected static void consume (int value)
    {
        System.out.print(MessageFormat.format("", new Object[]{new Integer(value)}));
    }

    /**
     * Starts one test.
     */
    protected static void startBench (String header)
    {
        System.out.print(header);
        helper = new HelperC();
        start = System.currentTimeMillis();
        divider = 1;
    }

    /**
     * Checks if the benchmark can stop (enough precision obtained).
     */
    protected static boolean checkEnd ()
    {
        span = System.currentTimeMillis() - start;

        if (span >= 1000)
            return true;
        else
        {
            ++divider;
            return false;
        }
    }

    /**
     * Ends one test.
     */
    protected static void endBench ()
    {
        consume(Helper.x);
        System.out.print(fmt.format(span / 100.0 / divider));
    }
}