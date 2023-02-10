
public class Sum
{
    public static void main( String[] argv )
    {
        long i = 5766; // 5765 is OK with -Xss8k,
                       // 5766: StackOverflowError
                       //       with 1024 entries in StackTrace
                       //       with -Xss8k ... -Xss40m

        try
        {
            System.out.println("sum("+i+") = "+sum(i));
        }
        catch (StackOverflowError e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static long sum(long i)
    {
        // System.out.println("sum("+i+")");
        if (i<2)
        {
            return i;
        }
        return i + sum(i-1);
    }
}
