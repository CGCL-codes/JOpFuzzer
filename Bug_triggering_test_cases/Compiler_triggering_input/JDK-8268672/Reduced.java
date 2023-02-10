public class Reduced {
    boolean b;
    double d;
    int iArr[];

    public static void main(String[] args) {
        Reduced t = new Reduced();
        for (int i = 0; i < 10; i++) {
            t.test();
        }
    }

    void test() {
        int e = 4, f = -51874, g = 7, h = 0;

        for (; f < 3; ++f) {
        }
        while (++g < 2) {
            if (b) {
                d = h;
            } else {
                iArr[g] = e;
            }
        }
        System.out.println(g);
    }
}
