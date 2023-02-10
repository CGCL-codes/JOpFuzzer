import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import static java.lang.invoke.MethodHandles.Lookup.ClassOption.*;

public class LotsUnload {
    static byte[] classdata;
    static int exitAfterNumClasses = 1024;

    public static void main(String args[]) throws Throwable {
        String resname = DefinedAsHiddenKlass.class.getName() + ".class";
        //System.out.println(resname);
        classdata = LotsUnload.class.getClassLoader().getResourceAsStream(resname).readAllBytes();
        //System.out.println(classdata.length);

        int numThreads = 4;
        try {
            numThreads = Integer.parseInt(args[0]);
        } catch (Throwable t) {}

        try {
            exitAfterNumClasses = Integer.parseInt(args[1]);
        } catch (Throwable t) {}

        for (int i = 0; i < numThreads; i++) {
            Thread t = new Thread() {
                    public void run() {
                        while (true) {
                            try {
                                Lookup lookup = MethodHandles.lookup();
                                Class<?> cl = lookup.defineHiddenClass(classdata, false, NESTMATE).lookupClass();
                                cl.newInstance();
                                add();
                            } catch (Throwable t) {
                                t.printStackTrace();
                            }
                        }
                    }
                };
            t.start();
        }
    }

    static int n;
    static synchronized void add() {
        n++;
        if (n >= exitAfterNumClasses) {
            System.exit(0);
        }
    }
}

class DefinedAsHiddenKlass {
    // ZGC region size is always a multiple of 2MB on x64
    static byte[] array = new byte[2 * 1024 * 1024 - 8 * 1024];
    static String x;
    static double d = 123;
    static float f = 456;
    public DefinedAsHiddenKlass() {
        x = "array size is "  + array.length + " bytes ";
    }
    public void doit(Runnable r) {
        r.run();
    }
}
