
public class ThreadTest {

    public int launchThread(final int id, final int sleepTime) {

        // Create a new thread object
        Thread t = new Thread(new Runnable() {
            public void run() {
                // Loop forever
                while(true) {
                    // Print the ID
                    System.out.print("" + id);
                    // Do some processing
                    for(int i = 0; i < 1000; i++) {
                        for(int j = 0; j < 1000; j++) {
                            double x = (double)i * j / j + i - j * j;
                        }
                    }
// Sleep for the amount specified in the sleep time

                    try {
                        Thread.currentThread().sleep(sleepTime);
                    }
                    catch(InterruptedException e) {
                        ;
                    }
                }
            }
        });
        // Start the thread
        t.start();
        return 0;
    }

    public static void main(String[] args) {
        // Create a Thread that will print the ms time every 30 seconds
        Thread timer = new Thread(new Runnable() {
            public void run() {
                // Print the begin time
                long start = System.currentTimeMillis();
                System.out.println("Start time: " + start);
                try {
                    // Sleep for 10 minutes
                    Thread.currentThread().sleep(300000);
                }
                catch(InterruptedException e) {
                    ;
                }
                // Print the current time
                System.out.println("time: " + (System.currentTimeMillis() -
                  start));
                System.exit(0);
            }
        });
        // Start the timer thread
        // Remove this line of code when it is not desired to stop the app after
10 minutes.
        timer.start();
        // Create a new test base object
        ThreadTest tb = new ThreadTest();

        // Launch a thread with a 5 ms sleep time
        tb.launchThread(1, 5);

        // Launch a thread with a 10 ms sleep time
        tb.launchThread(2, 10);
    }
}
