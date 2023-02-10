public class regresab66 implements Cloneable {

    static java.io.PrintStream log_out;
 
    static long limit_time_seconds = 30;
    static long limit_time_millis = limit_time_seconds * 1000;
 
    public static Object dummy;
    static volatile int test_result;

  static class regresab66a extends Thread {
    String thread_name;

    public regresab66a(String p_thread_name) {
        thread_name = p_thread_name;
    }

    public void run() {
        log_out.println("--> regresab66: Thread " + thread_name + " started");
        long test_beg_time_millis = System.currentTimeMillis();
        for (long i = 1; i > 0; i++) {
            long test_execution_time_millis = System.currentTimeMillis() - test_beg_time_millis;
            if ( test_execution_time_millis >= limit_time_millis ) {
                log_out.println("\n--> regresab66: test execution time elapsed!");
                long execution_time_second = (long)(test_execution_time_millis / 1000);
                long execution_time_minute = (long)(execution_time_second / 60);
                execution_time_second = execution_time_second - (execution_time_minute * 60);
                log_out.println("--> regresab66: test execution time = "
                    + execution_time_minute + "m. " + execution_time_second + "s");
                log_out.println("--> total number of made iterations = "+i);
                test_result = 0/*STATUS_PASSED*/;
                break;
            }
        }
    }
  }

    public static int run(String argv[], java.io.PrintStream out) {

        log_out = out;

        log_out.println("==> nsk/regression/regresab66 test LOG:");

        log_out.println
            ("--> regresab66: test execution time limit = " + limit_time_seconds + " seconds");

        test_result = 2/*STATUS_FAILED*/;
        regresab66a t = new regresab66a("\"regresab66a\"");
        t.start();

        while ( t.isAlive() ) {
            dummy = new int[32768];
            if ( test_result != 2/*STATUS_FAILED*/ ) {
                break;
            }
        }
        if ( test_result == 2/*STATUS_FAILED*/ ) {
            log_out.println("==> nsk/regression/regresab66 test FAILED");
        }
        else {
            log_out.println("==> nsk/regression/regresab66 test PASSED");
        }
        return test_result;
    }

    public static void main(String argv[]) {
        System.exit(run(argv, System.out) + 95/*STATUS_TEMP*/);
    }

} // end class regresab66