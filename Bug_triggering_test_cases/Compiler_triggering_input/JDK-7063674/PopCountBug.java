import java.util.*;
import java.io.*;
import java.util.zip.*;

public class PopCountBug {

    public static void main(String args[]) throws IOException {
        Random r = new Random(42);
        for (int i = 0; i < 10000000; i++) {
            score(r.nextLong(), r.nextLong(), r.nextLong(), r.nextLong());
            int res = score(19331613728L, 6237928679L, 272696352L, 8385412327L);
            if (res != 2) {
                System.out.println(i);
                System.out.println(res);
                return;
            }
        }
    }

    static final long sMask = (1L << 35) - 1;
    private static int score(final long r0, final long r1, final long template0, final long template1) {
        final long diff = simpleDiff(r0, r1, template0, template1) & sMask;
        final int exactScore = Long.bitCount(diff);
        if (exactScore <= 2) {
            return exactScore;
        }
        final long diffDel = diff & simpleDiff(r0 >>> 1, r1 >>> 1, template0, template1);
        final int scoreDel = Long.bitCount(diffDel);
        final long diffIns = diff & simpleDiff(r0, r1, template0 >>> 1, template1 >>> 1);
        final int scoreIns = Long.bitCount(diffIns);
        final int minA = Math.min(scoreDel, scoreIns);
        final int bestScore = Math.min(exactScore, minA + 1);
        if (r0 == 19331613728L && bestScore != 2) {
            System.out.println("scoreDel: " + scoreDel + " scoreIns: "+ scoreIns + " min(scoreDel, scoreIns): " + minA);
        }

        return (int) bestScore;
    }

    static long simpleDiff(final long a0, final long a1, final long b0, final long b1) {
        final long diff = (a0 ^ b0) | (a1 ^ b1);
        return diff;
    }
}