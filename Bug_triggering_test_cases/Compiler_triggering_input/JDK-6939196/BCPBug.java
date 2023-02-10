import java.dyn.*;
import static java.dyn.MethodHandles.*;
import static java.dyn.MethodType.*;

public class BCPBug {
    // Local class which appears in method signature;
    static class Example { }
    static void bcpTypesOnly(Object x) { System.out.println("bcpTypesOnly"); }
    static void hasUserType(Example x) { System.out.println("hasUserType"); }

    public static void main(String... av) throws Throwable {
        Lookup lookup = lookup();
        MethodHandle bcpTypesOnly = lookup.findStatic(lookup.lookupClass(), "bcpTypesOnly", methodType(void.class, Object.class));
        MethodHandle hasUserType = lookup.findStatic(lookup.lookupClass(), "hasUserType", methodType(void.class, Example.class));

        bcpTypesOnly.<void>invokeExact((Object)null);
        hasUserType.<void>invokeExact((Example)null); // throws NoClassDefFoundError on BCPBug$Example
    }
}
