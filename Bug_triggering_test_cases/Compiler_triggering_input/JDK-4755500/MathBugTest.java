public class MathBugTest {

    public static void main(String[] args) {
        System.out.println(Math.round(1d)); // prints "1", as expected
        for (int i=0; i<2000; i++) {
            Math.round(Double.NaN);
        }
        System.out.println(Math.round(1d)); // prints "0", which is wrong
    }

}