public class ShlI {
    public static int value = 11;
    public static final int shift = 38;
    public static final int LOOPS = 10000;

    static void foo() {
        for (int i=0; i<LOOPS; i++) {
            if (i==LOOPS-1) {
                value = 11;
                System.out.print( value + " << " + shift + " = " );
            }
            value<<=shift;
            if (i==LOOPS-1) {
                System.out.println(value);
            }
        }
    }
    public static void main(String args[]) {
        for (int i=0; i<20; i++) {
            foo();
        }
    }
}