
/**
 * @author Curtis Caravone
 */
public class ClassDeadlockTest {

    public static void main(String[] args) {
        if (args.length != 1) {
            throw new RuntimeException("Usage:  ClassDeadlockTest delay_millis (try 500 and 1500)");
        }
        int delayMillis = Integer.valueOf(args[0]);
        System.out.println("Delay = " + delayMillis + " millis");

        new Thread() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread() + "Created super: " + new Super());
            }
        }.start();

        try {
            Thread.sleep(delayMillis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(Thread.currentThread() + "Creating sub...");
        System.out.println(Thread.currentThread() + "Created sub: " + new Sub());
    }

    public static class Super {

        public static final String hello = getHelloString();

        private static String getHelloString() {
            int sleepTime = 1000;
            System.out.println(Thread.currentThread() + "Sleeping in Super static init for " + sleepTime + " millis");
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread() + "Creating new Sub()");
            return "Sub Instance = " + new Sub();
        }
    }

    public static class Sub extends Super {
        public Sub() {
            System.out.println(Thread.currentThread() + "In Sub constructor");
        }
    }

}
