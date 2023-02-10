public class Test {
    public static int f;

    public static void main(String... strArr) {
        for (int c = 0; c < 2; c++) {
            f += c;
            f *= -1;
        }
        f &= f;
        System.out.println("final = " + f);
    }
}
