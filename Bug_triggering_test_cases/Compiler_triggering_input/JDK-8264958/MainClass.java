class MainClass {
    int a;
    int b;
    short c;

    void test() {
        for (int i = 0; i < 10; ++i) {
            a = c;
            if (i > 1) {
                b = 0;
            }
            c = (short)(b - 7);
        }
        a--;
    }

    public static void main(String[] args) {
        MainClass t = new MainClass();
        for (int i = 0; i < 100_000; ++i) {
            t.test();
        }
    }
}

