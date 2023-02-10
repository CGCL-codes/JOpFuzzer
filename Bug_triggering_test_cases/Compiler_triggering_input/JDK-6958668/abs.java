public class abs {
    public Object copy() {
        return new abs();
    }

    static {
        for (int i = 0; i < 10000000; i++) {
            abs qc = new abs();
            Object o = qc.copy();
        }
    }

    public static void main(String[] args) {
    }
}