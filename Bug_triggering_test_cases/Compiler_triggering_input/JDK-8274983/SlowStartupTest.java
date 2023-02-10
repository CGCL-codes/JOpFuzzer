// authored by Oli Gillespie (ogillesp@amazon.co.uk)
import java.util.regex.Pattern;

/**
 * Demonstrates a strange issue with lower compilation tiers on some JDK versions.
 * 
 * E.g. with JDK17 -XX:TieredStopAtLevel=1 this takes around 200ms per 10k iterations,
 * whereas on JDK11 the same thing takes around 2 milliseconds.
 * Profile it with async-profiler and you'll see huge amounts of time in 
 * `SharedRuntime::handle_wrong_method_ic_miss` and `SharedRuntime::resolve_virtual_call_C`
 * for `java.util.regex.Pattern$BmpCharPredicate$$Lambda$22.0x80000002b.is(int)`.
 * 
 * Possibly interesting, on the fast runs I see 
 * `java.util.regex.Pattern$$Lambda$21.0x80000002a.is(int)` as the frame name, and on slow runs I see
 * `java.util.regex.Pattern$BmpCharPredicate$$Lambda$22.0x80000002b.is(int)`.
 */
public class SlowStartupTest {
    public static void main(String[] args) {
        Pattern p = Pattern.compile("[A-Za-z0-9]+");
        // Weirdly other similar patterns don't show the same behaviour, eg.
        // [A-Za-z]+
        // [a-z0-9]+
        //
        int repeat = -1; 
        int i = 0; 
        if (args.length > 0) {
            try {
                repeat = Integer.parseInt(args[0]);
            } catch (NumberFormatException ex) {
                System.err.println("wrong argument" + ex);  
            }
        }

        while ( repeat < -1 || i < repeat ) {
            long start = System.currentTimeMillis();
            for (int j = 0; j < 10_000; j++) {
                if (!p.matcher(Integer.toString(j)).matches()) {
                    System.out.println(":)");
                }
            }

            // 1~3 millisecond is reasonable, > 100ms is very slow
            System.out.printf("Executed 10000 iterations in %dms\n", System.currentTimeMillis() - start);
            i++;
        }
    }
}
