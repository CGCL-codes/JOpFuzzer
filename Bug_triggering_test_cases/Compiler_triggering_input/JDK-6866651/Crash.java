public class Crash {

    static int sum() {
        int s = 0;
        for (int x = 1, y = 0; x != 0; x++, y--) {
            s ^= y;
        }
        return s;
    }

    public static void main(final String[] args) {
        for (int k = 0; k < 2; k++) {
            System.err.println(String.valueOf(sum()));
        }
    }
}