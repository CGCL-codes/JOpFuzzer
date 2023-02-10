class TestAVX { 
        public static class SystemInfo{ 
                static { 
                        System.loadLibrary("SystemInfo"); 
                } 
                public native int getNumberOfHardwareThreads(); 
        } 

        static class TraceCacheSizeEstimator { 
        private static final float CORE_WEIGHT_KNL_HOST = .5f; 
        static final float LIVE_SAMP_FRAC = 213f; 
        static final float FAST_OUTSAMPS = 1.e6f; 

        public final long defaultTraceCacheSize; 
        public final long minimumTraceCacheSize; 

        public final long traceObjectSize; 
        public TraceCacheSizeEstimator( 
                                       int numberOfSamples, int numberOfHorizonPoints, int numberOfBins, int numberOfFilters, 
                                       SystemInfo systemInfo) { 

            float averageNumberOfOutputLocationsInAperture = 13213213.21f; 
            float averageNumberOfLiveSamplesInAperture = LIVE_SAMP_FRAC * numberOfSamples * averageNumberOfOutputLocationsInAperture; 
            averageNumberOfLiveSamplesInAperture = Math.max(averageNumberOfLiveSamplesInAperture, FAST_OUTSAMPS); 
            traceObjectSize = 25665; 
            float hostMicEquivalents = getHostMicEquivalents(systemInfo); 
            float allMicEquivalents = hostMicEquivalents ; 

            defaultTraceCacheSize = (long) (allMicEquivalents * 3423.90f); 
            minimumTraceCacheSize = (long) (allMicEquivalents * 3213213.f); 
        } 
        private float getHostMicEquivalents(SystemInfo systemInfo ){ 
            return CORE_WEIGHT_KNL_HOST * Math.min(1.0f, 2.0f * 256 / systemInfo.getNumberOfHardwareThreads()); 
        } 
    } 

    private static TraceCacheSizeEstimator create(){ 
        int numberOfSamples = 15; 
        int numberOfHorizonPoints = 1; 
        int numberOfBins = 81; 
        int numberOfFilters = 4; 
        return new TraceCacheSizeEstimator( 
                                           numberOfSamples, numberOfHorizonPoints, numberOfBins, numberOfFilters, 
                                           new SystemInfo()); 

    } 
    public static void main(String[] argv) throws Exception { 
        TraceCacheSizeEstimator trc = create(); 

        System.out.println(String.format("%d", trc.defaultTraceCacheSize)); 
    } 
