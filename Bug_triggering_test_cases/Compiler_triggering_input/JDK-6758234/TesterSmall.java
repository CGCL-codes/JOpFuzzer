public class TesterSmall {
    static boolean var_bad_static;

    public static void main(String[] args)
    {
        boolean var_good_local;
        long res;

// wrong result in case static var is used
        var_bad_static = true;
        res = ( (1 > ((var_bad_static) ? 1L : 0)) ? 1 : 0);
        System.out.println("var_bad_static = " + var_bad_static);
        System.out.println("res = " + res);
        System.out.println();

        // correct result in case local var is used
        var_good_local = true;
        res = ( (1 > ((var_good_local) ? 1L : 0)) ? 1 : 0);
        System.out.println("var_good_local = " + var_good_local);
        System.out.println("res = " + res);
        System.out.println();
    }
}
