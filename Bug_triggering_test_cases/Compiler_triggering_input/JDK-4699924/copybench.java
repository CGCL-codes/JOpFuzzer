public class copybench
{
    static char[] source = new char[2000];
    static char[] target = new char[5000];

    public static long run(int loopcount, boolean usesystem, int source_off,
                           int target_off, int len)
    {
        int indx, indx2;
        long starttime = System.currentTimeMillis();

        char[] s = source;
        char[] t = target;
        for (indx2=loopcount; --indx2 >= 0; )
        {
            if (! usesystem)
            {
                for (indx = 0; indx < len; ++indx)
                {
                    t[target_off + indx] = s[source_off + indx];
                }
            }
            else
            {
                System.arraycopy(s, source_off, t, target_off, len);
            }
        }
        long endtime = System.currentTimeMillis();
        return endtime - starttime;
    }

    public static void main(String argv[])
    {
        int indx;
        for (indx=0; indx < source.length; ++indx)
        {
            source[indx] = (char) indx;
        }
        System.out.println((argv.length == 0) + " warmup " + (long) argv.length);
        run(2,true,0,0,2000);
        run(2,false,0,0,2000);
        for (indx=100000; indx > 0; --indx)
        {
            run(1,true,1,1,16); // warm up run
            run(1,false,1,1,16); // warm up run
        }
        System.out.println("start");
        for (indx=10; indx > 0; --indx)
        {
            System.out.println("system copy " + run(99990,true,240,200,20));
            System.out.println("java copy " + run(99990,false,240,200,20));
        }
    }

}
