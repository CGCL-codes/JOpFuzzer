import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

public class DekkerVarTest {

    /*
      Read After Write Test (basically a simple Dekker test with volatile variables with VarHandle).
      Derived from the original jcstress test, available at:
        http://hg.openjdk.java.net/code-tools/jcstress/file/6c339a5aa00d/
        tests-custom/src/main/java/org/openjdk/jcstress/tests/varhandles/DekkerTest.java
     */

    static final int ITERATIONS = 1000000;
    //static final int ITERATIONS = 10;

    static final VarHandle VH_A, VH_B;

    static {
        try {
            VH_A = MethodHandles.lookup().findVarHandle(TestData.class, "a", int.class);
            VH_B = MethodHandles.lookup().findVarHandle(TestData.class, "b", int.class);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    static class TestData {
        public volatile int a;
        public volatile int b;
    }

    static class ResultData {
        public int a;
        public int b;
    }

    TestData[]   testDataArray;
    ResultData[] results;

    volatile boolean start;

    public DekkerVarTest() {
        testDataArray = new TestData[ITERATIONS];
        results = new ResultData[ITERATIONS];
        for (int i = 0; i < ITERATIONS; ++i) {
            testDataArray[i] = new TestData();
            results[i] = new ResultData();
        }
        start = false;
    }

    public void reset() {
        for (int i = 0; i < ITERATIONS; ++i) {
            testDataArray[i].a = 0;
            testDataArray[i].b = 0;
            results[i].a = 0;
            results[i].b = 0;
        }
        start = false;
    }

    int actor1(TestData t) {
         VH_A.setVolatile(t, 1);
         return (int)VH_B.getVolatile(t);
    }

    int actor2(TestData t) {
        VH_B.setVolatile(t, 1);
        return (int)VH_A.getVolatile(t);
    }

    class Runner1 extends Thread {
        public void run() {
            do {} while (!start);
            for (int i = 0; i < ITERATIONS; ++i) {
                results[i].a = actor1(testDataArray[i]);
            }
        }
    }

    class Runner2 extends Thread {
        public void run() {
            do {} while (!start);
            for (int i = 0; i < ITERATIONS; ++i) {
                results[i].b = actor2(testDataArray[i]);
            }
        }
    }

    void testRunner() {
        Thread thread1 = new Runner1();
        Thread thread2 = new Runner2();
        thread1.start();
        thread2.start();
        do {} while (!thread1.isAlive());
        do {} while (!thread2.isAlive());
        start = true;
        Thread.yield();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            System.out.println("interrupted!");
            System.exit(1);
        }
    }

    boolean printResult() {
        int[] count = new int[4];
        for (int i = 0; i < ITERATIONS; ++i) {
            int event_kind = (results[i].a << 1) + results[i].b;
            //System.out.format("results[%d].a=%d, results[%d].b=%d\n", i, results[i].a, i, results[i].b);
            ++count[event_kind];
        }
        if (count[0] == 0 && count[3] == 0) {
            System.out.println("[not interesting]");
            return false; // not interesting
        }
        String error = (count[0] == 0) ? " ok" : " disallowed!";
        System.out.println("[0,0] " + count[0] + error);
        System.out.println("[0,1] " + count[1]);
        System.out.println("[1,0] " + count[2]);
        System.out.println("[1,1] " + count[3]);
        return (count[0] != 0);
    }

    public static void main(String args[]) {
        DekkerVarTest test = new DekkerVarTest();
        final int runs = 20;
        int failed = 0;
        for (int c = 0; c < runs; ++c) {
            test.testRunner();
            if (test.printResult()) {
                failed++;
            }
            test.reset();
        }
        if (failed > 0) {
            throw new InternalError("FAILED. Got " + failed + " failed ITERATIONS");
        }
        System.out.println("PASSED.");
    }

}
