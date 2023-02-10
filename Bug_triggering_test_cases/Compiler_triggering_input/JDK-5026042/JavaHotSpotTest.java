public class JavaHotSpotTest {
    public static void main(String args[]) {
        for ( int p = 0; p < 10000 ; p++) {
            test();
            System.out.println("loop -----" + p);
        }
    }

    public static void test() {

        int period[] = {1, 2};

        // this one gives hotspot error
        int extra = java.lang.Math.max(3, 4);

        // this one has no hotspot error
        //int extra = myMax(3, 4);

        int zeroes[] = new int[10];
        int d;

        for (int i = 0 ; i < 10; i++ ) {
            for (int j=0; j < 2; j++) {
                d = zeroes[i];
                if (i > period[j]) {
                    d = zeroes[i - period[j]];
                }
            }
        }
        return;
    }


    public static int myMax( int a, int b ) {
        return (a > b)? a : b;
    }
}