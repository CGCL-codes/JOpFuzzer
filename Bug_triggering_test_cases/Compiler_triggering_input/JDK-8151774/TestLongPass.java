class TestLongPass {
    public static final long goldValue = (0x31414L << 32) | 0x31415;

    public static void checkLong2(int i1, int i2, long j3) {
        System.out.printf("%s: got %x, should be %x\n", j3 == goldValue ? "OK" : "Fail", j3, goldValue);
    }

    public static void main(String[] args) {
        checkLong2(0, 0, goldValue);
    }
}