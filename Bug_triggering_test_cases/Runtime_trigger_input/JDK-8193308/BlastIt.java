import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import sun.misc.Signal;
import sun.misc.SignalHandler;

public class BlastIt {
    public static void main(String[] args) {
        while(true) {
            doit();
        }
    }

    public static void doit() {
        Signal s = new Signal("BUS");
        Handler h = new Handler();
        SignalHandler orig = null;
        try {
                orig = Signal.handle(s, h);
                Signal.raise(s);
            if (h.sema.tryAcquire(100L, TimeUnit.MILLISECONDS)) {
                System.out.println("Got it");
            } else {
                System.out.println("Naah");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Signal.handle(s, orig);
        }
    }

    static class Handler implements SignalHandler {
        public Semaphore sema = new Semaphore(0);

        @Override
        public void handle(Signal signal) {
            System.out.println("Blasted");
            sema.release();
        }
    }
}
