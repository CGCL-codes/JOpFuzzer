import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class Test implements Cloneable {
    public void test() {
        System.out.println(this);
    }

    static final MethodHandle target;
    static {
        try {
            target = MethodHandles.lookup().findVirtual(Test.class, "test", MethodType.methodType(void.class));
        } catch (ReflectiveOperationException e) {
            throw new Error(e);
        }
    }
    static void run() throws Throwable {
        target.invokeExact((Test) null);
    }

    public static void main(String[] args) throws Throwable {
        for (int i = 0; i<15000; i++) {
            try {
              run();
            } catch (NullPointerException e) {
                // ignore
            }
        }
    }
}
