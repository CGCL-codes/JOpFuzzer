/* Test for range check elimination with i >= limit */
/* Run with -Xcomp -XX:CompileOnly=rce8.test1
 -XX:MaxInlineSize=1 */
public class rce8
{
    static int[] box = {-1,0,1,2,3,4,5,6,7,8,0x80000000};
    private static int result=0;
    public static int test1(int[] b)
    {
        int indx;
        int sum = b[5];
        int min = b[10];
        result = sum;
        for (indx = b.length-1; indx >= 0; --indx)
        {
            if (indx >= min)
            {
                sum += (sum ^ 9) + ((result != 0) ? 0 :sum);
                result = sum;
            }
            else
                throw new RuntimeException();
        }
        return sum;
    }
    public static void main(String[] args)
    {
        int r = test1(box);
        if (result != 16393)
        {
            System.out.println(result);
            System.exit(2);
        }
        else
        {
            System.out.println("WORKED");
        }
    }
}