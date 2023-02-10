
public class ThreadTester implements Runnable {

        int priority;

        public void run() {
                Thread.currentThread().setPriority(priority);
                for(int i=0; i<50000; i++) {
                        System.out.println(priority+" "+i);
//Optionally yield
                        Thread.yield();
                }
                System.out.println(priority+" done.");
        }

        public static void main(String[] args) {
                ThreadTester low=new ThreadTester();
                low.priority=Thread.MIN_PRIORITY;

                ThreadTester high=new ThreadTester();
                high.priority=Thread.MAX_PRIORITY;

                Thread tlow=new Thread(low);
                Thread thigh=new Thread(high);

                System.out.println("Executing Low");

                tlow.start();

                System.out.println("Sleeping");

                try {
                        Thread.sleep(20);
                } catch (InterruptedException ie) {

                }

                System.out.println("Executing high");
                thigh.start();
        }
}

