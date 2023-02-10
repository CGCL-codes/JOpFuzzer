class Reduced {
    public static void main(String[] strArr) {
        Reduced _instance = new Reduced();
        for (int i = 0; i < 10000; i++) {
            _instance.test();
        }
    }

    void test() {
        int i8 = 1;
        try {
            ;
        } finally {
            for (; i8 < 100; i8++) {
            }
        }
    }
}
