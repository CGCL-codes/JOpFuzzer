public class TestFinalFloat {
    
    public static void main(String... args) throws InterruptedException {
        new TestFinalFloat().go();
    }
    
    public final Object[][] storage;
    
    /**
     * Number of objects per region.
     */
    public final int K = 10;
    
    /**
     * Length of object array: sizeOf(Object[N]) ~= regionSize / K .
     */
    public final int N;
    
    /**
     * How many regions involved into testing.
     */
    public final int regionCount;
    
    TestFinalFloat() {
        
        long regionSize = 1_000_000; //WB.g1RegionSize();

        Runtime rt = Runtime.getRuntime();
        long used = rt.totalMemory() - rt.freeMemory();
        long totalFree = rt.maxMemory() - used;
        regionCount = (int) ( (totalFree / regionSize) * 0.9);
        int refSize = 4; 
        
        N = (int) ((regionSize / K ) / refSize) - 5; 
        storage = new Object[regionCount * K][];
        for (int i = 0; i < storage.length; i++) {
            storage[i] = new Object[N];
        }
    }

    public void go() throws InterruptedException {

        int[] regToRegRefCounts = {0, 100, 200, 300, 0};
        final float FINAL = getValue(); 
        
        
        System.out.println("Final value: " + FINAL);
        for (int i = 1; i < regToRegRefCounts.length; i++) {
            int pre = regToRegRefCounts[i-1];
            int cur = regToRegRefCounts[i];
            for (int to = 0; to < regionCount; to++) {
                Object celebrity = storage[to*K];
                for (int from = 0; from < regionCount; from++) {                    
                    int step = cur > pre ? +1 : -1;
                    for (int rn = pre; rn != cur; rn+=step) {
                        storage[getY(to, from, rn)][getX(to, from, rn)] = celebrity;
                    }                                       
                }
            }
            if (FINAL != getValue()) {
                System.out.println("Final values has changed: " + FINAL);
                System.out.println("TEST FAILED");
                System.exit(1);
            } else {
                System.out.println("Still passed");
            }
        }
        
    }
    
    public float getValue() {
        return 6;
    }
    
    private int getX(int to, int from, int rn) {
        return (rn*regionCount + to) % N;
    }    
    
    private int getY(int to, int from, int rn) {
        return ((rn*regionCount + to) / N + from * K) % (regionCount*K) ;
    }    
}

