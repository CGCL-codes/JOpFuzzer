class Reduced {
    int a = 400;
    static int counter = 0;
    long b[] = new long[a];

    void c(String[] d) {
        int e = 0, f, g, h, i[] = new int[a];
        long j;
        f = 2;
        b[f] = e;
        b = b;
        for (j = 301; j > 2; j -= 2) {
            g = 1;
            do {
                for (h = (int) j; h < 1; h++) {
                }
            } while (++g < 4);
        }
        counter++;
        if (counter == 100000) {
            throw new RuntimeException("expected");
        }
    }

    public static void main(String[] k) {
        try {
            Reduced l = new Reduced();
            for (;;) {
                l.c(k);
            }
        } catch (RuntimeException ex) {
            // Expected
        }
    }
}

