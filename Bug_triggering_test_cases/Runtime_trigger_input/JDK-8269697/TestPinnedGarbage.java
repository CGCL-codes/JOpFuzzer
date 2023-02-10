
public class TestPinnedGarbage {
    static {
        System.loadLibrary("TestPinnedGarbage");
    }
    
   private static final int OBJS_COUNT = 10;

    private static native void pin(Object[] a);
    private static native void unpin(Object[] a);

    public static void main(String[] args) {
        Object[] objs = new Object[OBJS_COUNT];
        for (int i = 0; i < OBJS_COUNT; i++) {
            objs[i] = new MyClass();
        }

        pin(objs);
        System.out.println("Object array pinned");
        unpin(objs);
    }

    public static class MyClass {
        public Object ref = new Object();
    }

}
