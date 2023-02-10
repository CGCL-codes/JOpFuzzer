/* Test for range check elimination with i <= limit */
/* Run with -Xcomp -XX:CompileOnly=rce7.test1
 -XX:MaxInlineSize=1 */
public class rce7
{
    static int[] box = {1,2,3,4,5,6,7,8,9,0x7fffffff};
    public static int result=0;
    public static int test1(int[] b)
    {
        int indx;
        int max = b[9];
        int sum = b[7];
        result = sum;
        for (indx = 0; indx < b.length; ++indx)
        {
            if (indx <= max)
            {
                sum += (indx ^ 15) + ((result != 0) ? 0 : sum);
                result = sum;
            }
            else
                throw new RuntimeException();
        }
        for (indx = -7; indx < b.length; ++indx)
        {
            if (indx <= 9)
            {
                sum += (sum ^ 15) + ((result != 0) ? 0 : sum);
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
        if (result != 14680079)
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