public class Reduced2 {
    static final int ZERO  = 0b0000_0000_0000_0000_0000_0000_0000_0000;
    // Must have at least one bit on.
    static final int INPUT = 0b0000_0000_0000_0000_0000_0000_0000_0001;
    // Must have at least one of the bits in range 31..22 on.
    static final int MASK  = 0b0000_0000_1000_0000_0000_0000_0000_0000;
    // Must be >= 9.
    static final int N = 9;
    // Must be odd (r[i] alternates between INPUT and INPUT ^ MASK) and >= 65
    // (to trigger the faulty superword optimization?).
    static final int M = 65;
    static final int EXPECTED = (M % 2 == 0 ? INPUT : INPUT ^ MASK);
    static int mask = 0;
    static void test() {
        int j = 0;
        int r[] = new int[N];
        for (int i = 0; i < N; i++) {
            r[i] = INPUT;
        }
        for (int k = 0; k < MASK; k++) {
            Reduced2.mask++;
        }
        for (int i = 0; i < N; i++) {
            for (j = 0; j < M; j++) {
                r[i] ^= Reduced2.mask;
            }
        }
        for (int i = 0; i < N; i++) {
            assert(r[i] == EXPECTED);
        }
        System.out.println(Integer.toString(j) + j);
        return;
    }
    public static void main(String[] strArr) {
        for (int i = 0; i < 10; i++) {
            Reduced2.mask = ZERO;
            Reduced2.test();
        }
    }
}
