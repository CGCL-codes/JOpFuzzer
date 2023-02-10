/*
 * @test
 * @summary Crash.
 * @library /test/lib
 * @build sun.hotspot.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller sun.hotspot.WhiteBox
 * @run main/othervm/timeout=300 -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI Test
 */

import sun.hotspot.WhiteBox;

public class Test {

    public static void main(String[] args) {
        WhiteBox.getWhiteBox().enqueueInitializerForCompilation(LongWrapper.class, 4);
    }


    static class LongWrapper {
        final static LongWrapper ZERO = new LongWrapper(0);
        private long val;

        LongWrapper(long val) {
            this.val = val;
        }

        static LongWrapper wrap(long val) {
            return (val == 0L) ? ZERO : new LongWrapper(val);
        }
    }
}
