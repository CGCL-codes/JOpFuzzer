public class Tester {
    static byte var_1;
    static String var_2 = "";
    static byte var_3;
    static float var_4 = 0;

    public static void main(String[] args)
    {
        int i = 0;

        for (String var_tmp = var_2; i < 11; var_1 = 0, i++)
        {
            var_2 = var_2;
            var_4 *= (var_4 *= (var_3 = 0));
        }

        System.out.println("var_1 = " + var_1);
        System.out.println("var_2 = " + var_2);
        System.out.println("var_3 = " + var_3);
        System.out.println("var_4 = " + var_4);
    }
}