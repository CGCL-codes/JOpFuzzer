import java.util.*;

public class ArrayCopyMicroBenchmark {
    abstract static class Job {
        private final String name;
        Job(String name) { this.name = name; }
        String name() { return name; }
        abstract void work() throws Throwable;
    }

    private static void collectAllGarbage() {
        try {
            for (int i = 0; i < 2; i++) {
                System.gc(); System.runFinalization(); Thread.sleep(10);
            }
        } catch (InterruptedException e) { throw new Error(e); }
    }

    /**
     * Runs each job for long enough that all the runtime compilers
     * have had plenty of time to warm up, i.e. get around to
     * compiling everything worth compiling.
     * Returns array of average times per job per run.
     */
    private static long[] time0(Job ... jobs) throws Throwable {
        final long warmupNanos = 10L * 1000L * 1000L * 1000L;
        long[] nanoss = new long[jobs.length];
        for (int i = 0; i < jobs.length; i++) {
            collectAllGarbage();
            long t0 = System.nanoTime();
            long t;
            int j = 0;
            do { jobs[i].work(); j++; }
            while ((t = System.nanoTime() - t0) < warmupNanos);
            nanoss[i] = t/j;
        }
        return nanoss;
    }

    private static void time(Job ... jobs) throws Throwable {

        long[] warmup = time0(jobs); // Warm up run
        long[] nanoss = time0(jobs); // Real timing run

        final String nameHeader = "Method";
        int nameWidth = nameHeader.length();
        for (Job job : jobs)
            nameWidth = Math.max(nameWidth, job.name().length());

        final String millisHeader = "Millis";
        int millisWidth = millisHeader.length();
        for (long nanos : nanoss)
            millisWidth =
                    Math.max(millisWidth,
                            String.format("%JniStaticContextFloat.c", nanos/(1000L * 1000L)).length());

        final String ratioHeader = "Ratio";
        int ratioWidth = ratioHeader.length();

        String format = String.format("%%-%ds %%%dd %%.3f%%n",
                nameWidth, millisWidth);
        String headerFormat = String.format("%%-%ds %%-%ds %%-%ds%%n",
                nameWidth, millisWidth, ratioWidth);
        System.out.printf(headerFormat, "Method", "Millis", "Ratio");

// Print out absolute and relative times, calibrated against first job
        for (int i = 0; i < jobs.length; i++) {
            long millis = nanoss[i]/(1000L * 1000L);
            double ratio = (double)nanoss[i] / (double)nanoss[0];
            System.out.printf(format, jobs[i].name(), millis, ratio);
        }
    }

    private static int intArg(String[] args, int i, int defaultValue) {
        return args.length > i ? Integer.parseInt(args[i]) : defaultValue;
    }

    private static void deoptimize(Object[] a) {
        for (Object x : a)
            if (x == null)
                throw new Error();
    }

    public static void main(String[] args) throws Throwable {
        final int iterations = intArg(args, 0, 100000);
        final int size = intArg(args, 1, 1000);
        final Object[] array = new Object[size];
        final Random rnd = new Random();
        for (int i = 0; i < array.length; i++)
            array[i] = rnd.nextInt(size);

        time(
                new Job("arraycopy") { void work() {
                    Object[] a = array;
                    for (int i = 0; i < iterations; i++) {
                        Object[] t = new Object[size];
                        System.arraycopy(a, 0, t, 0, size);
                        a = t;}
                    deoptimize(a);}},
                new Job("copyOf") { void work() {
                    Object[] a = array;
                    for (int i = 0; i < iterations; i++)
                        a = Arrays.copyOf(a, size);
                    deoptimize(a);}},
                new Job("clone") { void work() {
                    Object[] a = array;
                    for (int i = 0; i < iterations; i++)
                        a = a.clone();
                    deoptimize(a);}},
                new Job("loop") { void work() {
                    Object[] a = array;
                    for (int i = 0; i < iterations; i++) {
                        Object[] t = new Object[size];
                        for (int j = 0; j < size; j++)
                            t[j] = a[j];
                        a = t;}
                    deoptimize(a);}}
        );
    }
}