public class Test {

    static final MethodHandle MH_m;

    static {
        try {
            MH_m = lookup().findStatic(MyInterface.class, "m", MethodType.methodType(void.class));
        } catch (ReflectiveOperationException e) {
            throw new BootstrapMethodError(e);
        }
    }

    public static void main(String[] args) throws Throwable {
        for (int i = 0; i < 20_000; i++) {
            payload();
        }
    }

    static void payload() throws Throwable {
        MH_m.invokeExact();
    }

}

interface MyInterface {
    static void m() {}
}
