public class Crashes130SolarisWithHotSpot {
    public static void main(String[] argv)
    {
        System.out.println("First Loop");
        for (long i = 0; i < 1; i++)
        {
            for (long j = 1; j < 192 - i; j++)
            {
                Object o = new Object();
                for (long k = i; k < i + j; k++)
                {
                    if (i == 0 && j == 166 && k > 120)
                        System.out.println("i=" + i + ", j=" + j + ", k=" + k);
                    if (k > 192)
                    {
                        System.out.println("BUG!!!");
                        System.exit(-1);
                    }
                }
            }
        }

        System.out.println("Second Loop");
        for (long i = 0; i < 1; i++)
        {
            for (long j = 0; j < 1; j++)
            {
                Object o = new Object();
            }
        }
        System.out.println("Success!!");
    }
}