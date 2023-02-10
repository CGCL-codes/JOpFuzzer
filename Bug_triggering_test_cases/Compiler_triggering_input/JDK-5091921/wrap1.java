/* Run with -Xcomp -XX:CompileOnly=wrap1.test1 -XX:MaxInlineSize=1 */
/* limit reset to ((limit-init+stride-1)/stride)*stride+init */
/* Calculation may overflow */
public class wrap1
{
    public static volatile int c = 1;
    public static int test1(int limit)
    {
        int indx;
        int sum = 0;
        for (indx = 0xffffffff; indx < limit; indx += 0x20000000)
        {
            sum += c;
        }
        return sum;
    }
    public static void main(String[] args)
    {
        int result;
        result = test1(0x7fffffff);
        if (result != 4)
        {
            System.out.println(result);
            System.out.println("FAILED");
            System.exit(1);
        }
        else
        {
            System.out.println("WORKED");
        }
    }
}