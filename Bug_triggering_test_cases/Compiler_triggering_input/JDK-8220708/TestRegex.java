import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestRegex {

	public static void main(String[] args) throws Exception {

		long startTime = System.currentTimeMillis();
		int numOfThreads = 4;
		ExecutorService executor = Executors.newFixedThreadPool(numOfThreads);
		System.out.println("Starting benchmark...");

		String s1 = "( wtime = 1h:4m:51s , utime = 3814.79s , stime = 36.29s , RM = 3539 , VM=4218 , MaxRSS=4912316 , MaxVSZ=5154912 , DiskIOReadKB=0 , DiskIOWriteKB=0 , avgovertime(30s;VM)= 4206.166666666667 , avgovertime(30s;desiredCpu)= 1.0 , avgovertime(5m;desiredCpu)= 1.0 , avgovertime(60m;VM)= 3586.9467787114845 , deltaovertime(60m;utime+stime)= 3583.0 , avgovertime(2m;desiredCpu)= 1.0 , avgovertime(1m;desiredCpu)= 1.0 , avgovertime(60m;RM)= 3054.817415730337 , deltaovertime(15m;utime+stime)= 896.0 , avgovertime(5m;VM)= 4108.1 , avgovertime(30s;RM)= 3527.0 , deltaovertime(5m;utime+stime)= 295.0 , avgovertime(5m;RM)= 3430.483333333333 , avgovertime(2m;RM)= 3495.9166666666665 , avgovertime(15m;VM)= 3864.866666666667 , avgovertime(2m;VM)= 4174.833333333333 , avgovertime(15m;RM)= 3194.822222222222 , deltaovertime(2m;utime+stime)= 114.0 , Max(avgovertime(5m;RM))= (3508;2536) , Max(avgovertime(30s;VM))= (4252;2536) , Max(avgovertime(5m;VM))= (4153;2536) , Max(avgovertime(2m;desiredCpu))= (1;144) , Max(avgovertime(15m;RM))= (3222;2536) , Max(avgovertime(60m;RM))= (3054;3883) , Max(avgovertime(2m;VM))= (4221;2536) , Max(deltaovertime(2m;utime+stime))= (117;2526) , Max(avgovertime(60m;VM))= (3586;3888) , Max(avgovertime(2m;RM))= (3573;2536) , Max(avgovertime(30s;desiredCpu))= (1;69) , Max(avgovertime(15m;VM))= (3864;3888) , Max(avgovertime(1m;desiredCpu))= (1;84) , Max(avgovertime(30s;RM))= (3735;459) , Max(avgovertime(5m;desiredCpu))= (1;339) , Max(deltaovertime(5m;utime+stime))= (297;2706) , Max(deltaovertime(15m;utime+stime))= (897;3808) , Max(deltaovertime(60m;utime+stime))= (3584;3747) , trmGB= 755.96 , freeCoresCap= 0.5 , freeMemCap= 356 , LocalDirsTotalMB= 827.36 , frmPer= 64.45 , time= 03/07/19-10:22:05 , frmGB= 487.23 , memoryCons= 3.3145182291666666 , coresCons= 0.9966666666666667 )";
		String s2 = " Max\\(avgovertime\\(5m;RM\\)\\)";

		for (int j=0; j<numOfThreads; j++)
		{
			executor.execute(new Runnable() {
				@Override
				public void run() {
					for(int i=0; i<50000; i++) {
						if (i % 1000 == 0)
						{
							System.out.print(".");
						}
						boolean result = Boolean.valueOf(s1.matches("[\\d\\D]*" + s2 + "[\\d\\D]*"));
					}
				}
			}
					);
		}

		executor.shutdown();
		while (!executor.isTerminated()) {
		}
		System.out.println("Finished all threads");

		long finishTime = System.currentTimeMillis();
		System.out.println("Finished running benchmark - took " + (finishTime-startTime) + " millis");
	} 
}
