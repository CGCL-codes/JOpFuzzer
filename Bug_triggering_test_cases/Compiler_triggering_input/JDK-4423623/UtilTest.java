public class UtilTest
{
    public static long power2(int x) {
        long l = 1L;
        for (int i = 1; i <= x; i++)
            l += l;
        return l;
    }
    
    public static void main(String[] strings) {
        System.out.println(power2(5));
   }
}