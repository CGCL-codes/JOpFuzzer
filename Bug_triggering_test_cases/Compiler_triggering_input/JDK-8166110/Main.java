import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class Main {

    public static void main(String[] args) throws Throwable {
        MethodHandle h = makeLoop();
        for (int i = 0; i < 100; ++i) {
            System.err.print(i);
            h.invoke();
            System.err.println();
        }
    }

    static MethodHandle makeLoop() throws Throwable {
        MethodHandle it = MethodHandles.constant(int.class, 10000);
        MethodHandle bo =
                MethodHandles.lookup().findStatic(Main.class, "body", MethodType.methodType(void.class, int.class));
        return MethodHandles.countedLoop(it, null, bo);
    }

    static void body(int x) {
        if (x % 100 == 0) {
            System.err.print('.');
        }
    }

}