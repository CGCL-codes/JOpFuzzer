public class TestShortRShift
{
    static final int BUFFER_SIZE = 1024;
    static final int WARMUP_ITER = 3000*BUFFER_SIZE;
    static final int ITER = 100_000*BUFFER_SIZE;
    static short[] inputBuffer = new short[BUFFER_SIZE];
    static short[] outputBuffer =  new short[BUFFER_SIZE];
    

    public static double runTest(long numIterations) {
        long time1, time0; 
        time0 = System.nanoTime();
        mainTest(numIterations);
        time1 = System.nanoTime();
        double throughput = 1f*numIterations/(time1-time0)*1e9;
        return throughput;
    }

    public static void mainTest(long numIterations) {
        while (numIterations > 0) {
            for (int i = 0; i < BUFFER_SIZE; i++) {
                outputBuffer[i] = (short)(inputBuffer[i] >> 3);
            }
            numIterations--;
        }
    }
   
    public static void main(String args[]) {
        // Initalize input and output buffers
        java.util.Locale.setDefault(java.util.Locale.UK);
        for (int i=0; i<BUFFER_SIZE; i++) {
            inputBuffer[i] = (short)0x8765;
            outputBuffer[i] = 0x1111;
        }
        // Command line arguments
        boolean verbose = args.length>0 && args[0].toLowerCase().equals("-v");
        final int numIterations = ITER * Integer.parseInt(args[1]); 
        
        double throughput;
        // Warmup iterations
        if (verbose) System.out.println("Warmup...");
        throughput = runTest(WARMUP_ITER);
        if (verbose) System.out.printf("throughput: %3f iter/sec\n", throughput);

        // Main iterations
        if (verbose) System.out.println("Benchmarking...");
        throughput = runTest(numIterations);
        if (verbose) System.out.printf("throughput: %3f iter/sec\n", throughput);
            else System.out.printf("%3d iter/sec\n", throughput);
            
        // Verify results
        if (verbose) System.out.println("Verifying the result...");
        for (int i=0; i<BUFFER_SIZE; i++) {
            if (outputBuffer[i] != ((short)0x8765 >> 3)) {
                System.out.println("Computing error(index="+i+")!");
                break;
            }
        }
    }
}
