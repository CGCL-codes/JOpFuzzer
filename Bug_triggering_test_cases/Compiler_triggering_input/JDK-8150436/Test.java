import static java.lang.invoke.MethodHandles.*;
import static java.lang.invoke.MethodType.methodType;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.Objects;

public final class Test {

    static class RunnableImpl implements Runnable {
        public int count = 0;

        @Override
        public void run() {
            count++;
        }
    }

    private Test(RunnableImpl impl) {
        this.impl = impl;
    }

    private final RunnableImpl impl;

    RunnableImpl get() {
        return impl;
    }

    public static void main(String... args) throws Throwable {
        /* This test creates a MethodHandle that executes the same code like:
         *
         * void composite(Test arg) {
         * Runnable r = (Runnable) arg.get();
         * if (Objects.nonNull(r)) {
         * r.run();
         * } else {
         * // fake else clause for MethodHandles.guardWithTest():
         * noop(r);
         * }
         * }
         */

        final Lookup lookup = lookup();
        MethodHandle getter_MH = lookup.findVirtual(Test.class, "get", methodType(RunnableImpl.class));
        // cast to Runnable:
        getter_MH = getter_MH.asType(getter_MH.type().changeReturnType(Runnable.class));

        // MH with "invokeinterface"
        MethodHandle run_MH = lookup.findVirtual(Runnable.class, "run", methodType(void.class));

        // MH for null check:
        MethodHandle nonNullTest_MH = lookup.findStatic(Objects.class, "nonNull", methodType(boolean.class, Object.class))
                .asType(methodType(boolean.class, Runnable.class));

        // MH to implement fake else check with noop(Runnable):
        MethodHandle noop_MH = dropArguments(constant(Void.class, null).asType(methodType(void.class)), 0, Runnable.class);

        // our composite MH doing null check:
        MethodHandle composite_MH = filterReturnValue(getter_MH, guardWithTest(nonNullTest_MH, run_MH, noop_MH));

        // invoke our handle in loop executing null/non-null branch alternately:
        Test t1 = new Test(new RunnableImpl());
        Test t2 = new Test(null);
        int iters = 10_000;
        for (int i = 0; i < iters; i++) {
            Test t = (i % 2 == 0) ? t1 : t2;
            composite_MH.invokeExact(t);
        }
        assert t1.get().count == iters / 2;
    }

}