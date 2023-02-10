/* Test for the bug of transforming indx <= MAXINT to indx < MAXINT+1 */
/* Run with -Xcomp -XX:CompileOnly=le1.test1 -XX:MaxInlineSize=1 */
public class le1
{
    public static int result = 0;
    public static int test1(int limit)
    {
        int indx;
        int sum = 0;
        for (indx = -500; indx <= limit; indx += 2)
        {
            sum += 3000 / indx;
            result = sum;
        }
        return sum;
    }
    public static void main(String[] args)
    {
        int r = 0;
        try
        {
            r = test1(0x7fffffff);
            System.out.println(result);
            System.out.println("FAILED");
            System.exit(1);
        }
        catch (ArithmeticException e1)
        {
            System.out.println("Expected exception caught");
            if (result != -9039)
            {
                System.out.println(result);
                System.out.println("FAILED");
                System.exit(1);
            }
        }
        System.out.println("WORKED");
    }
}