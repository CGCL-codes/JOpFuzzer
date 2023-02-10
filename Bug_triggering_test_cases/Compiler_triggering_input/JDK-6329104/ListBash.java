import java.util.*;

public class ListBash {
    static final Random rnd = new Random();
    static int numItr;
    static int listSize;

    public static void main(String[] args) throws Throwable {
        numItr = Integer.parseInt(args[0]);
        listSize = Integer.parseInt(args[1]);

        oneRun();
        oneRun();
        oneRun();
        oneRun();
    }

    static void oneRun() {
        long startTime = System.nanoTime();
        for (int i=0; i<numItr; i++)
            elementLoop();
        long elapsed = System.nanoTime() - startTime;
        System.out.println("Time: " + (elapsed/1000000000.0) + "s");
    }

    static void elementLoop() {
        List<Integer> s1 = new ArrayList();
        AddRandoms(s1, listSize);

        List<Integer> s2 = new ArrayList();
        AddRandoms(s2, listSize);

        clone(s1);
    }

    // Done inefficiently so as to exercise toArray
    static List<Integer> clone(List<Integer> s) {
        List a = Arrays.asList(s.toArray());

        List<Integer> clone = new ArrayList();
        clone.addAll(a);
        if (!s.equals(clone))
            fail("List not equal to copy.");
        if (!s.containsAll(clone))
            fail("List does not contain copy.");

        return clone;
    }

    static void AddRandoms(List<Integer> s, int n) {
        for (int i=0; i<n; i++) {
            int r = rnd.nextInt() % n;
            Integer e = new Integer(r < 0 ? -r : r);

            int preSize = s.size();
            if (!s.add(e))
                fail ("Add failed.");
            int postSize = s.size();
            if (postSize-preSize != 1)
                fail ("Add didn't increase size by 1.");
        }
    }

    static void fail(String s) {
        System.out.println(s);
        System.exit(1);
    }
}