



        import java.lang.management.ThreadInfo;
        import java.lang.management.ManagementFactory;

public class LockTest2 {
    private Object syncObj = new Object();

    public LockTest2() {
    }

    public synchronized void test() {

        int localVar0, localVar1, localVar2, localVar3, localVar4, localVar5, localVar6, localVar7, localVar8, localVar9;
        int localVar10, localVar11, localVar12, localVar13, localVar14, localVar15, localVar16, localVar17, localVar18, localVar19;
        int localVar20, localVar21, localVar22, localVar23, localVar24, localVar25, localVar26, localVar27, localVar28, localVar29;
        int localVar30, localVar31, localVar32, localVar33, localVar34, localVar35, localVar36, localVar37, localVar38, localVar39;
        int localVar40, localVar41, localVar42, localVar43, localVar44, localVar45, localVar46, localVar47, localVar48, localVar49;

        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + ": Entering test()");

        int n = 0;
        for (int i = 0; i < 10000000; i++)
            n += i;
        System.out.println(threadName + ": Leaving test(), n=" + n);
        return;
    }

    public static void logMonitors() {
        String threadName = Thread.currentThread().getName();
        java.lang.management.MonitorInfo[] monitors = java.lang.management.ManagementFactory.getThreadMXBean()
                .getThreadInfo(new long[]{Thread.currentThread().getId()}, true, true)[0].getLockedMonitors();
        System.out.println(threadName + ": Number of monitors: " + monitors.length);
        for (int i = 0; i < monitors.length; i++) {
            java.lang.management.MonitorInfo monitor = monitors[i];
            System.out.println(threadName + ": MonitorInfo[" + i + "]=" + monitor.toString() + ", StackDepth=" + monitor.getLockedStackDepth()
                    + ", frame=" + ((monitor.getLockedStackFrame() == null) ? "No Frame" : monitor.getLockedStackFrame().toString()));
        }
        return;
    }

    public static void main(String[] argv) {
        LockTest2 lt = new LockTest2();
        RunLockTest rlt = new RunLockTest(lt, 10000);
        Thread t = new Thread(rlt);
        System.out.println("main: About to start first thread: " + t.getName());
        t.start();

        synchronized (rlt) {
            while (!rlt.finishedTest) {
                try {
                    rlt.wait();
                } catch (Exception e) {
                }
            }
        }

        rlt = new RunLockTest(lt, 0);
        t = new Thread(rlt);
        System.out.println("main: About to start second thread: " + t.getName());
        t.start();
        while (t.getState() != Thread.State.TERMINATED) {
            try {
                Thread.currentThread().sleep(1000);
            } catch (Exception e) {
            }
            ThreadInfo[] tis = ManagementFactory.getThreadMXBean().getThreadInfo(new long[]{t.getId()}, true, true);
            if (tis.length == 0)
                System.out.println("Unable to find the thread " + t.getName());
            else {
                ThreadInfo ti = tis[0];
                if (ti == null)
                    System.out.println("Unable to get info for thread: " + t.getName());
                else
                    System.out.println(t.getName() + " state: " + ti.getThreadState().toString() +
                            ", LockName: " + ti.getLockName() + ", LockOwnerName: " + ti.getLockOwnerName());
            }
        }
        // Never gets here
        System.out.println("Exiting main()");
        return;
    }

    public static class RunLockTest implements Runnable {
        public LockTest2 lt;
        public boolean finishedTest = false;
        public long sleepLength;

        public RunLockTest(LockTest2 _lt, long _sleepLength) {
            lt = _lt;
            sleepLength = _sleepLength;
        }

        public void run() {
            logMonitors();
            lt.test();
            logMonitors();
            synchronized (this) {
                finishedTest = true;
                this.notify();
            }
            if (sleepLength > 0) {
                try {
                    Thread.currentThread().sleep(sleepLength);
                } catch (Exception e) {
                }
            }
            System.out.println(Thread.currentThread().getName() + ": exiting");
            return;
        }
    }

}

