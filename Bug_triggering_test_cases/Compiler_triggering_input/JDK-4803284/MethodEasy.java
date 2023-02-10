/**
 * "Easy" test: Uses only compile-time constants for loop bounds, making
 * things easier for loop optimizations reliant on static analysis.
 *
 * (c) 2002 by Osvaldo Pinali Doederlein.
 * Released to public domain.
 */
public class MethodEasy extends MethodBench
{
    /**
     * Declare the benchmark instance here, init later, to fight dataflow
     optimizations.
     */
    static MethodEasy t;

    /**
     * Optimizer-friendly constant for the loop's max bound.
     */
    static final int CALLS = 100000000;

    /**
     * Optimizer-friendly constant for the loops' lower bounds.
     */
    static final int ZERO = 0;

    /**
     * Optimizer-friendly constant for the benchmark controller's loop.
     */
    static final int CYCLES = 5;

    /**
     * Tests interface method.
     */
    final void benchInterface ()
    {
        HelperInterface helper = (Math.random() < 0.5) ?
                (HelperInterface)new HelperB() : (HelperInterface)new HelperD();

        for (int i = ZERO; i < CALLS; ++i)
            helper.polyHardF(i);
    }

    /**
     * Tests "truly" polymorphic virtual method.
     */
    final void benchTrulyPoly ()
    {
        final int calls = CALLS / 1000;
        HelperC helper = null, h1 = new HelperC(), h2 = new HelperD();

        for (int i = 0; i < 1000; ++i)
        {
            switch (i % 500)
            {
                case 0: helper = h1; break;
                case 1: helper = h2; break;
            }

            for (int j = ZERO; j < calls; ++j)
                helper.polyEasyF(i);
        }
    }

    /**
     * Tests polymorphic virtual method.
     */
    final void benchPoly ()
    {
        for (int i = ZERO; i < CALLS; ++i)
            helper.polyEasyF(i);
    }

    /**
     * Tests virtual method.
     */
    final void benchVirtual ()
    {
        for (int i = ZERO; i < CALLS; ++i)
            helper.virtualEasyF(i);
    }

    /**
     * Tests non-virtual method.
     */
    final void benchFinal ()
    {
        for (int i = ZERO; i < CALLS; ++i)
            helper.finalEasyF(i);
    }

    /**
     * Tests static method.
     */
    final void benchStatic ()
    {
        for (int i = ZERO; i < CALLS; ++i)
            Helper.staticEasyF(i);
    }

    /**
     * Benchmark startup.
     */
    public static void main (String[] args)
    {
        System.out.println("*** Testing (Easy) ***");

        for (int i = ZERO; i < CYCLES; ++i)
            bench();
    }

    /**
     * Benchmark controller for "Easy" tests.
     */
    static void bench ()
    {
        t = new MethodEasy();
        new HelperD().polyEasyF(1);
        consume(Helper.x);

        startBench("Interface: ");
        do { t.benchInterface(); } while (!checkEnd());
        endBench();

        startBench(" TrulyPoly: ");
        do { t.benchTrulyPoly(); } while (!checkEnd());
        endBench();

        startBench(" Poly: ");
        do { t.benchPoly(); } while (!checkEnd());
        endBench();

        startBench(" Virtual: ");
        do { t.benchVirtual(); } while (!checkEnd());
        endBench();

        startBench(" Final: ");
        do { t.benchFinal(); } while (!checkEnd());
        endBench();

        startBench(" Static: ");
        do { t.benchStatic(); } while (!checkEnd());
        endBench();

        System.out.println();
    }
}
