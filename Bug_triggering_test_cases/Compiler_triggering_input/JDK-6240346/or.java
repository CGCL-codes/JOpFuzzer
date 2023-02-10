public class or {
    static public int v = 0;
    static public int l = 10;
    static public int e = 0;

    public static void main(String[] args) {
        for (int i = 0; i < 100000; i++) {
            logic_or(i % 10);
        }
        for (int i = 0; i < 100000; i++) {
            bitwise_or(i % 10);
        }
    }
    public static void bitwise_or(int x) {
        if ((x < 0) | x >= l) {
            throw new InternalError("out of range " + x);
        } else {
            e++;
        }
    }
    public static void logic_or(int x) {
        if ((x < 0) || x >= l) {
            throw new InternalError("out of range " + x);
        } else {
            e++;
        }
    }
}