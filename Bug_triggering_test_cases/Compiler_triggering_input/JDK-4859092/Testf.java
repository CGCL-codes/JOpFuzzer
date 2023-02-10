public class Testf {
    static final double value = 10000000.;

    private static void check( double l ) {
        if ( l != value )
            System.out.println("l("+l+") != Value("+value+")");
    }

    private static double foo( ) {
        double l = 0.;
        while(l < value) {
            l += 1.; // deoptimization happens here and the double 'l' value is
        } // stored to interpreter stack incorrectly on Sparc-v8plus
        return l;
    }

    public static void main(String arg[]) {
        System.out.println("Start!");
        for(int i=0; i < 100; i++) {
            System.out.println(i);
            System.gc();
            check( foo( ) );
        }
        System.out.println("Done!");
    }
}