public class UncommonTrapLeak {
    static WeakReference<Object> ref = null;
    static int val = 0;
    public static void main(String args[]) {
        for (int i = 0; i < 300; i++) {
            val++;
            foo(i);
            System.gc();
            if (ref.get() != null) {
                throw new RuntimeException("Failed: referent not collected after trap " + ref.get());
            }
            if (i % 100 == 0) {
                System.out.println(i);
            }
        }
    }

    static void foo(int i) {
        Object o = new Object();
        ref = new WeakReference<Object>(o);
        if (val == 200) {
            // trigger Deoptimization::uncommon_trap
            if (o instanceof UncommonTrapLeak) {
            }
        }
    }
}