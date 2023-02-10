public class AllocateInstance {
    // The number of iterations is high to ensure that tiered
    // compilation kicks in all the way up to C2.
    static final int ITERATIONS = 200_000;

    public static void main(String args[]) throws Exception {
        Class c = Class.forName("sun.misc.Unsafe");
        java.lang.reflect.Field f = c.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        sun.misc.Unsafe unsafe = (sun.misc.Unsafe)f.get(c);

        for (int i=0; i<ITERATIONS; i++) {
            // allocateInstance() on an abstract class should result in an InstantiationException
            try {
                AbstractClass ac = (AbstractClass) unsafe.allocateInstance(AbstractClass.class);
                System.out.println("Did not get expected InstantiationException for abstract class");
                break;
            } catch (InstantiationException e) {
                // Expected
            }
        }

        for (int i=0; i<ITERATIONS; i++) {
            try {
                AnInterface ai = (AnInterface) unsafe.allocateInstance(AnInterface.class);
                System.out.println("Did not get expected InstantiationException for interface");
                break;
            } catch (InstantiationException e) {
                // Expected
            }
        }
    }

    abstract class AbstractClass {
        protected AbstractClass() {}
    }

    interface AnInterface {}
}