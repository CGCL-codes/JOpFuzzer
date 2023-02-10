public class dowhile {
    public static void main(String[] args) {
        for (int i = 0; i < 10000000; i++) {
            testDoWhile();
        }
    }
    public static volatile boolean repeatLoop;
    static volatile int sideEffect;

    public static void testDoWhile() {
        do {
            sideEffect++;
        } while (repeatLoop);
    }
}
