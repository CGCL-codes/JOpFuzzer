import java.util.*;

public class TestRand extends Thread {

    private static String alpha = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private Random r;
    public int[] buckets;
    int nLoops;

    TestRand(int nLoops) {
        r = new Random();
        buckets = new int[4];
        buckets[0] = buckets[1] = buckets[2] = buckets[3] = 0;
        this.nLoops = nLoops;
    }

    public int random(int x, int y) {
        int n = r.nextInt();
        while (n == Integer.MIN_VALUE)
            n = r.nextInt();
        int ret = ( x + (Math.abs(n) % (y - x + 1)));
        return ret;
    }

    public double drandom(double x, double y) {
        return ( x + (r.nextDouble()* (y - x)));
    }

    public String make_a_string(int x, int y) {
        int len, i;
        String str = "";
        len = random(x, y);
        for (i = 0; i < len; i++) {
            int j = random(0, 61);
            str = str + alpha.substring(j, j + 1);
        }
        return str;
    }

    public int NURand(int A, int x, int y) {
        return ((random(0, A) | random(x, y)) % (y - x + 1)) + x;
    }

    public void run() {
        for (int i = 0; i < nLoops; i++) {
            int x = random(1, 100);
            if (x <= 10) {
                buckets[0]++;
            }
            else if (x <= 30) {
                buckets[1]++;
            }
            else if (x <= 50) {
                buckets[2]++;
            }
            else if (x <= 100) {
                buckets[3]++;
                make_a_string(10,20);
                make_a_string(10,20);
                make_a_string(10,20);
                make_a_string(10,20);
                make_a_string(10,20);
                random(1, 100);
            }
            drandom(0.0, 1.0);
        }
    }

    public static void main(String[] args) throws Exception {
        int nLoops = Integer.parseInt(args[0]);
        int nThreads = Integer.parseInt(args[1]);
        int[] agg = new int[4];

        TestRand[] tr = new TestRand[nThreads];
        agg[0] = agg[1] = agg[2] = agg[3] = 0;

        for (int i = 0; i < nThreads; i++) {
            tr[i] = new TestRand(nLoops);
            tr[i].start();
        }

        for (int i = 0; i < nThreads; i++) {
            tr[i].join();
            agg[0] += tr[i].buckets[0];
            agg[1] += tr[i].buckets[1];
            agg[2] += tr[i].buckets[2];
            agg[3] += tr[i].buckets[3];
        }

        System.out.println("Distribution:\n");
        System.out.println("\tBucket 1 (Expected 10%): " + ((float) agg[0]/(nLoops * nThreads)));
        System.out.println("\tBucket 2 (Expected 20%): " + ((float) agg[1]/(nLoops * nThreads)));
        System.out.println("\tBucket 3 (Expected 20%): " + ((float) agg[2]/(nLoops * nThreads)));
        System.out.println("\tBucket 4 (Expected 50%): " + ((float) agg[3]/(nLoops * nThreads)));
    }
}