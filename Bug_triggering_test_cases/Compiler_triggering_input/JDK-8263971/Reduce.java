class Reduce {
    int a = 400;

    public static void main(String[] h) {
        Reduce r = new Reduce();
        r.test();
    }

    void test() {
        int x = 6;
        boolean b[] = new boolean[a];
        for (int i = 68; i > 3; i--) {
            b[i] = true;
            try {
                x = 60 / i;
            } catch (ArithmeticException e) {
            }
        }
        System.out.println(x);
    }
}
