/* Test for range check elimination with bit flip issue for
   scale*i+offset<limit where offset is not 0 */
/* Run with -Xcomp -XX:CompileOnly=rce5.test1
 -XX:MaxInlineSize=1 */
public class rce5
{
    static int[] box = {1,2,3,4,5,6,7,8,9};
    public static int result=0;
    public static int test1(int[] b, int limit)
    {
        int indx;
        int sum = b[1];
        result = sum;
        for (indx = 0x80000000; indx < limit; ++indx)
        {
            if (indx > 0x80000000)
            {
                // this test is not issued in pre-loop but issued in main loop
                // trick rce into thinking expression is false when indx >= 0
                // in fact it is false when indx==0x80000001
                if (indx - 9 < -9)
                {
                    sum += indx;
                    result = sum;
                    sum ^= b[indx & 7];
                    result = sum;
                }
                else
                    break;
            }
            else
            {
                sum += b[indx & 3];
                result = sum;
            }
        }
        return sum;
    }
    public static void main(String[] args)
    {
        int r = test1(box,0x80000100);
        if (result != 3)
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