import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.List;


public class Main {

    private final int threadNumber;

    private final int internalIterationsNumber;

    // Used to sanity check results of set/get/add operations
    final int MAGIC_PRIME = 13;

    Main() {

        threadNumber = Runtime.getRuntime().availableProcessors();

        internalIterationsNumber = 10000;

    }


    public static void main(String[] args) throws Exception {
        new Main().execute();
    }


    void testDoubleField(VarHandle varHandle, Object obj, int idx) {

        double value = idx * MAGIC_PRIME;

        varHandle.compareAndExchange(obj, value, value);
        varHandle.compareAndExchangeAcquire(obj, value, value);
        varHandle.compareAndExchangeRelease(obj, value, value);
        VarHandle.storeStoreFence();

        verify((double) varHandle.getAndAdd(obj, value));
        verify((double) varHandle.getAndAddRelease(obj, value));
        verify((double) varHandle.getAndAddAcquire(obj, value));

        VarHandle.fullFence();
    }

    /*
     * Test atomic access from different threads using VarHandle.getXXX/setXXX methods.
     * The only sanity verification (result is MAGIC_PRIME * N) is done where it is possible.
     */
    void testFieldAccess() throws NoSuchFieldException, IllegalAccessException {
        Shared shared = new Shared();
        shared.str = "test";

        VarHandle dH = MethodHandles.lookup().in(Shared.class)
                .findVarHandle(Shared.class, "d", double.class);

        List<Thread> threads = new ArrayList<>();
        for (int threadIdx = 0; threadIdx < threadNumber; threadIdx++) {
            threads.add(new Thread(() -> {
                for (int i = 0; i < internalIterationsNumber; i++) {
                    testDoubleField(dH, shared, i);
                }
            }, "Thread"));
        }
        threads.forEach(Thread::start);
        threads.forEach(t -> {
            try {
                t.join();
            } catch (Throwable e) {
                throw new IllegalStateException(e);
            }
        });
    }

    void verify(double result) {
        if (Math.round(result) % MAGIC_PRIME != 0) {
            throw new Error("Incorrect result: '" + result + "'");
        }
    }


    protected void execute() throws Exception {

        while (true) {
            testFieldAccess();
        }
    }

}


class Shared {
    boolean z;
    char c;
    byte b;
    short s;
    int i;
    long l;
    float f;
    double d;
    int ib;
    long lb;
    String str;
}
