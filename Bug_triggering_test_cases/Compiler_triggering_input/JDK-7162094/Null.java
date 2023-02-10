public class Null {
    public static void main(String[] args) {
        Integer c = new Integer(-1);
        for (int i = 0; i < 1000000000; i++) {
            test(c);
        }
        test(null);
    }

    public static int test(Integer c) {
        return c;
    }
}
