import java.lang.invoke.*;

class A {
    public static String foo(){return "";}
}
public class Test {
    public static void main(String argv[]) throws Throwable {
        MethodHandle handle = MethodHandles.lookup().findStatic
                (A.class, "foo", MethodType.methodType(String.class));
        String inv1 = (String) handle.invokeExact(null);
// String inv2 = (String) handle.invokeExact(new Object[]{});
// String inv3 = (String) handle.invokeExact(-1);
// CharSequence cs = (CharSequence) handle.invokeExact();
// Object obj = handle.invokeExact();
    }
}