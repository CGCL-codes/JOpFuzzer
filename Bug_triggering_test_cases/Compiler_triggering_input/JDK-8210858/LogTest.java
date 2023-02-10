public class LogTest {
    public static void main(String[] args) {
        double x = 4.9E-324;
        System.out.println(Math.log(x));
        System.out.println(StrictMath.log(x));
    }
}