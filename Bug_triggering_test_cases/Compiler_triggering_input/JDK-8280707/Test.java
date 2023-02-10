public class Test {

    static primitive class EmptyInline {

    }

    public static void test1(EmptyInline[] emptyArray) {
        System.arraycopy(emptyArray, 0, emptyArray, 10, 10);
        System.arraycopy(emptyArray, 0, emptyArray, 20, 10);
    }

    public static void test2(EmptyInline[] emptyArray) {
        System.arraycopy(emptyArray, 0, emptyArray, 10, 10);
    }

    public static void main(String[] args) {
        EmptyInline[] emptyArray = new EmptyInline[100];
        for (int i = 0; i < 100_000; ++i) {
            test1(emptyArray);
            test2(emptyArray);
        }
    }
}
