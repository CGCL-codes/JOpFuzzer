public class b4336548 {
    static public boolean continue_loop = true;
    static Thread s_TesterThread;
    static Thread s_GCThread;
    static int s_started_threads_count;
    static boolean s_may_threads_to_run;
    static Object barrier = new Object();


    public static int run(String argv[], java.io.PrintStream out) {
        int v_test_result = 0/*STATUS_PASSED*/;
        int v_wait_minutes_limit = 5;
        int v_wait_seconds_interval_for_print = 20;

        s_started_threads_count = 0;
        s_may_threads_to_run = false;

        System.out.println("==> nsk/regression/b4336548 test LOG:");
        System.out.println("----> This test on #4336548 bug; Category: hotspot; Subcategory:runtime_system");
        System.out.println(" Synopsis: [solaris i386] Compiler Safepoints do not work onmultiprocessor systems\n");

        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        new b4336548().start();

        while ( s_started_threads_count != 2 ) {
            try {
                Thread.sleep(1000); //sleep for 1 second
            }
            catch (InterruptedException ix) {
            }
        }
        s_may_threads_to_run = true;

        int v_wait_current_seconds = 0;
        System.out.println("----> b4336548: waiting threads to finish...");
        while ( s_GCThread.isAlive() || s_TesterThread.isAlive() ) {
            try {
                Thread.sleep(1000); //sleep for 1 second
            }
            catch (InterruptedException ix) {
            }
            v_wait_current_seconds++;
            if ( v_wait_current_seconds >= (v_wait_minutes_limit * 60) ) {
                v_test_result = 2/*STATUS_FAILED*/;
                break;
            }
            if ( v_wait_current_seconds % v_wait_seconds_interval_for_print == 0 ) {
                System.out.println("----> b4336548: waiting threads to finish - "
                                                + v_wait_current_seconds + " seconds!");
            }
        }

        if ( v_test_result == 2/*STATUS_FAILED*/ ) {
            System.out.println("----> b4336548: threads NOT finished during "
                                                + v_wait_minutes_limit + " minutes!");
            System.out.println("==> nsk/regression/b4336548 test FAILED");
        }
        else {
            System.out.println("----> b4336548: Both threads finished!");
            System.out.println("==> nsk/regression/b4336548 test PASSED");
        }
        return v_test_result;
    }

    public static void main(String argv[]) {
        System.exit(run(argv, System.out) + 95/*STATUS_TEMP*/);
    }

    public void start() {
        s_TesterThread = new TesterThread();
        s_TesterThread.setPriority(Thread.MIN_PRIORITY);
        s_TesterThread.start();

        s_GCThread = new GCThread();
        s_GCThread .setPriority(Thread.MIN_PRIORITY);
        s_GCThread.start();
    }

    class TesterThread extends Thread {

        // trick compiler in believing that this is not and endless loop
        public void loop (int fict_int) {
            while ( continue_loop ) {
                if (fict_int == 0) {
                    continue_loop = false;
                }
            }
        }
        
        public void run () {
            System.out.println("----> b4336548: TesterThread started!");
            synchronized (b4336548.barrier) {
                s_started_threads_count++;
            }
            while ( ! s_may_threads_to_run ) {
                try {
                    Thread.sleep(1000); //sleep for 1 second
                }
                catch (InterruptedException ix) {
                }
            }
            loop(1);
            System.out.println("----> b4336548: TesterThread finished!");
        }
    }

    class GCThread extends Thread {
        public void run () {
            System.out.println("----> b4336548: GCThread started!");
            synchronized (b4336548.barrier) {
                s_started_threads_count++;
            }
            while ( ! s_may_threads_to_run ) {
                try {
                    Thread.sleep(1000); //sleep for 1 second
                }
                catch (InterruptedException ix) {
                }
            }
            long counter = 0;
            while (true) {
                try {
                    sleep(250);
                }
                catch (InterruptedException ix) {
                }
                System.gc();
                counter++;
                if (counter == 10) {
                    continue_loop = false;
                    System.out.println("----> b4336548: GCThread finished!");
                    return;
                }
            }
        }
    }
    
} // end of b4336548 class