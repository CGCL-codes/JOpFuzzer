/**
 * "Hard" test: Uses values not compilt-time computable for loop bounds, making
 * things harder for loop optimizations (favors profile-driven compilers).
 *
 * (c) 2002 by Osvaldo Pinali Doederlein.
 * Released to public domain.
 */
public class MethodHard extends MethodBench
{
    /**
     * Declare the benchmark instance here, init later, to fight dataflow
     optimizations.
     */
    static MethodHard t;

    /**
     * Optimizer-friendly constant for the loop's max bound.
     */
    static final int CALLS = Integer.parseInt("100000000");

    /**
     * Optimizer-friendly constant for the loops' lower bounds.
     */
    static final int ZERO = Integer.parseInt("0");

    /**
     * Optimizer-friendly constant for the benchmark controller's loop.
     */
    static final int CYCLES = Integer.parseInt("5");

    /**
     * Tests interface method.
     */
    final void benchInterface ()
    {
        int calls = CALLS / 1000;
        HelperInterface helper = null, h1 = new HelperB(), h2 = new HelperD();

        for (int i = 0; i < 1000; ++i)
        {
            switch (i % 500)
            {
                case 0: helper = h1; break;
                case 1: helper = h2; break;
            }

            for (int j = ZERO; j < calls; ++j)
                helper.polyHardF(i);
        }
    }

    /**
     * Tests "truly" polymorphic virtual method.
     */
    final void benchTrulyPoly ()
    {
        int calls = CALLS / 1000;
        Helper helper = null, h1 = new Helper(), h2 = new HelperA();
        Helper h3 = new HelperB(), h4 = new HelperC(), h5 = new HelperD();

        for (int i = 0; i < 1000; ++i)
        {
            switch (i % 200)
            {
                case 0: helper = h1; break;
                case 1: helper = h2; break;
                case 2: helper = h3; break;
                case 3: helper = h4; break;
                case 4: helper = h5; break;
            }

            for (int j = ZERO; j < calls; ++j)
                helper.polyHardF(i);
        }
    }

    /**
     * Tests polymorphic virtual method.
     */
    final void benchPoly ()
    {
        for (int i = ZERO; i < CALLS; ++i)
            helper.polyHardF(i);
    }

    /**
     * Tests virtual method.
     */
    final void benchVirtual ()
    {
        for (int i = ZERO; i < CALLS; ++i)
            helper.virtualHardF(i);
    }

    /**
     * Tests non-virtual method.
     */
    final void benchFinal ()
    {
        for (int i = ZERO; i < CALLS; ++i)
            helper.finalHardF(i);
    }

    /**
     * Tests static method.
     */
    final void benchStatic ()
    {
        for (int i = ZERO; i < CALLS; ++i)
            Helper.staticHardF(i);
    }

    /**
     * Benchmark startup.
     */
    public static void main (String[] args)
    {
        System.out.println("*** Testing (Hard) ***");

        for (int i = ZERO; i < CYCLES; ++i)
            bench();
    }

    /**
     * Benchmark controller for "Hard" tests.
     */
    static void bench ()
    {
        t = new MethodHard();
        new HelperD().polyHardF(1);
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