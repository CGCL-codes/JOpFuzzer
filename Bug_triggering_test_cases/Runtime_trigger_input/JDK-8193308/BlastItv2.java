import sun.misc.Signal;
import sun.misc.SignalHandler;

public class BlastItv2 {

    private static volatile int i;

    public static void main(String[] args) {

        Handler h = new Handler();

        try {
            Signal s = new Signal("BUS");
            Signal.handle(s, h);

            while (true) {
                for (i = 0; i < 10000000; ++i) {
                }
                System.out.println("Doing stuff");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class Handler implements SignalHandler {
        @Override
        public void handle(Signal signal) {
            System.out.println("SIGBUS!");
        }
    }
}
