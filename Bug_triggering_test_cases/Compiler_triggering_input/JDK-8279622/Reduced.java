public class Reduced {
    int b = 400;
    int c = 4;
    int u;

    public static void main(String[] s) {
        Reduced t = new Reduced();
        long prev = 0;
        for (int i = 0; i < 20; i++) {
            if (prev == 0) {
                prev = t.test();
            } else if (t.test() != prev) {
                throw new RuntimeException();
            }
        }
    }

    long test() {
        float f[] = new float[b];
        int q = 66, r[] = new int[b];
        for (int j = 0; j < r.length; j++) {
            r[j] = j;
        }
        for (int m = 100; m > 9; m -= 2) {
            int e[][] = new int[b][b];
        }

        for (int w = 8; w < 20000; w++) {
        }

        for (int x = 5; x < 84; x++) {
            for (q = 3; q < 68; q++) {
                r[x] ^= c;
            }
        }

        System.out.println(f + ",");
        long sum = 0;
        for (int j = 0; j < r.length; j++) {
            sum += r[j];
        }
        System.out.println(q + "iArr3 = " + sum);
        return sum;
    }
}
