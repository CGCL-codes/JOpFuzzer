
public class Test {

    public static void test() {
        for (long i = 0; i < 1000; ++i) {
            for (long j = 0; j < 1000; ++j) {
            }
            for (int k = 0; k < 32; k++) {
                System.out.println("");
            }
        }
        // Infinite loop
        while(true) { }
    }

    public static void main(String[] args) {
        test();
    }
}
