
public class TestReplay01 {
    private static final String emptyString;

    static {
        emptyString = "";
    }

    public static void m1() {
        m2();
    }

    public static void m2() {
        m3();
    }

    public static void m3() {

    }

    public static void main(String[] args) {

        // Trigger compilation of m1
        for (int i = 0; i < 10_000; ++i) {
            m1();
            if ((i % 1000) == 0) {
                System.out.println("Hello World!");
            }
        }
    }
}
