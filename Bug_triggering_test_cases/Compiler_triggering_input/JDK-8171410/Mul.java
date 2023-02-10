class Mul {
    static long multest(long a, long b) {
        long res = a;
        for (int i = 0; i < 100000000; i++) {
            res += Math.multiplyExact(a, b);
            a ^= 1L; b ^= 1L; // Stop loop invariant hoisting
        }
        return res;
    }
    public static void main(String argv[]) {
        long a = 0x5a5a5a5aL;
        long b = 0xa5a5a5a5L;
        System.out.println("res " + a + ", " + b + " = " + multest(a, b));
    }
}