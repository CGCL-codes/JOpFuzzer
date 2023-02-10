import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/**
 * Resident memory grows continuously at a rate of ~a few MB per second.
 *
 * jcmd <pid> VM.native_memory detail.diff
 *
 * [0x00007f07fe70ba3e] OopStorage::try_add_block()+0x2e
 * [0x00007f07fe70c0ad] OopStorage::allocate()+0x3d
 * [0x00007f07fe8cec14] ThreadSnapshot::initialize(ThreadsList*, JavaThread*)+0x1c4
 * [0x00007f07fe8cee29] ThreadDumpResult::add_thread_snapshot(JavaThread*)+0x69
 *                              (malloc=361MB type=Serviceability +293MB #599515 +486208)
 * --
 * [0x00007f07fe70ba3e] OopStorage::try_add_block()+0x2e
 * [0x00007f07fe70c0ad] OopStorage::allocate()+0x3d
 * [0x00007f07fe8cfc58] StackFrameInfo::StackFrameInfo(javaVFrame*, bool)+0x68
 * [0x00007f07fe8d07d4] ThreadStackTrace::dump_stack_at_safepoint(int)+0xe4
 *                              (malloc=1940MB type=Serviceability +1578MB #3219458 +2618513)
 */
public class LeakTestMinimal {
    public static void main(String[] args) {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        // I see about 4GB resident after 20MM iters
        for (int i = 0; i < 100_000_000; i++) {
            bean.getThreadInfo(bean.getAllThreadIds(), Integer.MAX_VALUE);
            if (i % 10_000 == 0) {
                System.out.println("Iteration " + i);
                System.gc();
            }
        }
    }
}
