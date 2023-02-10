public class ExTest
{
    int a;
    public static void main(String[] args)
    {
        try {
            ExTest test = null;
            test.a = 11;
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }
}