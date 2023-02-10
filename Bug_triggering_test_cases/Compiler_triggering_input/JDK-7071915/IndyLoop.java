import java.lang.invoke.MethodHandle;
import static java.lang.invoke.MethodHandles.*;
import static java.lang.invoke.MethodType.*;
import java.lang.invoke.MutableCallSite;

public class IndyLoop {
    public static void main(String[] args) throws Throwable {
        // method handle loop adds up numbers from 1 to args[0].to_i
        long max = args.length == 0 ? 1000000L : Long.parseLong(args[0]);
        
        MethodHandle loop = makeLoop();
        System.out.println((long)loop.invokeExact(max));
        System.out.println(addAll(max, 0));
    }
    
    private static long addAll(long max, long accum) {
        if (max > 0) {
            return addAll(dec(max), add(accum, max));
        } else {
            return accum;
        }
    }
    
    private static MethodHandle makeLoop() throws Exception {
        Lookup lookup = lookup();
        
        // backward branch
        MutableCallSite bottom = new MutableCallSite(methodType(long.class, long.class, long.class));
        MethodHandle mh = bottom.dynamicInvoker();
        
        // drop previous iteration's values
        mh = dropArguments(mh, 2, long.class, long.class);
        
        // decrement max
        MethodHandle dec = lookup.findStatic(IndyLoop.class, "dec", methodType(long.class, long.class));
        dec = permuteArguments(dec, methodType(long.class, long.class, long.class, long.class), new int[]{1});
        mh = foldArguments(mh, dec);
        
        // add both arguments
        MethodHandle add = lookup.findStatic(IndyLoop.class, "add", methodType(long.class, long.class, long.class));
        mh = foldArguments(mh, add);
        
        // compare to zero
        MethodHandle test = lookup.findStatic(IndyLoop.class, "gtZero", methodType(boolean.class, long.class));
        test = dropArguments(test, 1, long.class);
        
        // return accumulator
        MethodHandle fallback = dropArguments(identity(long.class), 0, long.class);
        mh = guardWithTest(test, mh, fallback);
        
        // rebind callsite
        bottom.setTarget(mh);
        
        // insert initial accum value
        return insertArguments(mh, 1, 0);
    }
    
    private static boolean gtZero(long a) {
        return a > 0;
    }
    
    private static long add(long a, long b) {
        return a + b;
    }
    
    private static long dec(long a) {
        return a - 1;
    }
}
