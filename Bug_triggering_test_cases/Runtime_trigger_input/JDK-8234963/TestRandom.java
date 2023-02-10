public class TestRandom {

    static int doCalc(long i)
    {
	int v = 0;
    	    try {
            // perform random math functions to use CPU
        	v = 4 * 67 + 87 / 45 * 2345;
        	if ((i%10) == 0)
        	{
        	    String className = Thread.currentThread().getStackTrace()[0].getClassName();
//        	    throw new Exception("A thousand");
        	}
            }
            catch (Exception e) {
            }
    	return v;
    }

    public static void main(String args[]) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            Math.random();
        }
        //System.out.println("Math.random() Time: " + (System.currentTimeMillis() - start) + "ms");

        start = System.currentTimeMillis();
        for (long i = 0; i < 1_000_000_000; i++) {
    	    int v = doCalc(i);
        }
        System.out.println("Simple Math Time: " + (System.currentTimeMillis() - start) + "ms");
    }
}
