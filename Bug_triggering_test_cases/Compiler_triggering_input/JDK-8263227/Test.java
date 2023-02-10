class Test {
    public static void test() {
        int a = Integer.valueOf(42).intValue();
        int b = (((a & 0x0000F000)) + 1);
        int c = a / b + ((a % b > 0) ? 1 : 0);
    }
    public static void main(String[] args) {
        for (int i = 0; i < 10_000; i++) {
            test();
        }
    }
}
