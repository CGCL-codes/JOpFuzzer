public class Optimize {

    private byte[] buffer = new byte[] {1, 0, 0, 0, 0, 0, 0, 0};

    public static void main(String[] args) {
        Optimize opt = new Optimize();
        long l = 0L;
        for (int i = 0; i < 1000000000; i++) {
            l += opt.getLong(i & 7);
        }
        System.out.println("l = " + l);
    }

    public Optimize() {}

    public long getLong(int i) {
        long b = buffer[i] & 0xFFL;
        return b << 32;
    }
}
