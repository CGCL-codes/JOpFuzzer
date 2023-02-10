import java.io.PrintStream;
import java.lang.management.*;

public class getTMB12 {

    public static int run(String argv[], PrintStream out) {
        ThreadMBean c = ManagementFactory.getThreadMBean();
        Thread trd1 = new Thread(), trd2 = new Thread(), trd3 = new Thread();
        long [] idArr = {trd1.getId(), trd2.getId(), trd3.getId()};

        try {
            trd3.setDaemon(!trd3.isDaemon());
            trd2.start(); trd3.start();
            trd2.join(); trd3.join();
        } catch (InterruptedException ie) {
            out.println("Unexpected exception" + ie);
            return 1;
        }
        try {
            if (!c.isThreadCpuTimeEnabled()) {
                c.setThreadCpuTimeEnabled(true);
            }
            for (int i = 0; i < idArr.length; i++) {
                out.println("info for thread ID " + idArr[i]);
                if (c.getThreadCpuTime(idArr[i]) != -1) {
                    out.println("incorrect thread's CPU time for ID = " + idArr[i]);
                    return 1;
                }
            }
        } catch (UnsupportedOperationException ue) {
            out.println("Passed with UnsupportedOperationException");
        }
        out.println("OKAY");
        return 0;
    }

    public static void main(String argv[]) {
        System.exit(run(argv, System.out) + 95/*STATUS_TEMP*/);
    }
}