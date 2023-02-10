public class WithFieldAccessorTest {

    public static final __ByValue class V {
        private final int i;
        V() {
            this.i = 0;
        }

        public static V make(int i) {
            V v = __MakeDefault V();
            v = __WithField(v.i, i);
            return v;
        }
    }

    public static void main(String... args) throws Throwable {
        V v = __WithField(V.make(10).i, 20);
        if (!v.toString().equals("[value class WithFieldAccessorTest$V, 20]"))
            throw new AssertionError("Withfield didn't work!" + v.toString());
    }
}