public class Test {

    public static Integer test() {
        Integer[] src = new Integer[10];
        src[0] = new Integer(42);
        Integer[] dst = (Integer[])src.clone();
        return dst[0];
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100_000; ++i) {
            test();
        }
    }
}