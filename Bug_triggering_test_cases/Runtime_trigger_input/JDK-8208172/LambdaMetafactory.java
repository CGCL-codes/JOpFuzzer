
import java.lang.invoke.LambdaConversionException;


public class LambdaMetafactory {
    static {
         throwNpe();
    }

    static void throwNpe(){
        throw new NullPointerException();
    }

    public static java.lang.invoke.CallSite metafactory(java.lang.invoke.MethodHandles.Lookup owner,
                                                            String invokedName,
                                                            java.lang.invoke.MethodType invokedType,
                                                            java.lang.invoke.MethodType samMethodType,
                                                            java.lang.invoke.MethodHandle implMethod,
                                                            java.lang.invoke.MethodType instantiatedMethodType)
            throws LambdaConversionException {
        return java.lang.invoke.LambdaMetafactory.metafactory(owner,
                invokedName,
                invokedType,
                samMethodType,
                implMethod,
                instantiatedMethodType);
    }
}