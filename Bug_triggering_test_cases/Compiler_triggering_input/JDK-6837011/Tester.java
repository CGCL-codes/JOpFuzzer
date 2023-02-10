class Tester {
    static boolean var_3 = true;

    public static void main(String[] args)
    {
        double var_5;
        char var_7 = 1;
        double var_11 = 0;

        do
        {
            var_11++;
            var_5 = (var_7 /= ( var_3 ? ~1L : 3 ) );
        } while (var_11 < 1);

        System.out.println("PASSED");
    }
}