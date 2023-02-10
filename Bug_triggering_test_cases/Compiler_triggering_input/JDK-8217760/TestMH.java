import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class TestMH {
    public static final MethodHandle MH;

    static {
        try {
            MH = MethodHandles.lookup().findVirtual(Object.class, "hashCode", MethodType.methodType(int.class));
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new Error(e);
        }
    }

    static int test1(Object o) {
        try {
            return (int)MH.invokeExact(o);
        } catch(Throwable e) {
            throw new Error(e);
        }
    }

    static int test2(Object o) {
        if (o instanceof Object) {}
        try {
            return (int)MH.invokeExact(o);
        } catch(Throwable e) {
            throw new Error(e);
        }
    }

    public static void main(String[] args) {
        Object o = new Object() { public int hashCode() { return 0; }};
        for (int i = 0; i < 100000; i++) {
            test1(o);
            test2(o);
        }
    }
}
