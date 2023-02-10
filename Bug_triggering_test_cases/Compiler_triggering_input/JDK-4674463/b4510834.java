import java.io.*;

public class b4510834 {
    private int WAITTIME = 2; // minutes
    private int THREADS = 50;

    public static void main(String args[]) {
        new b4510834().run(args);
    }

    private void run(String args[]) {
        for (int i=0; i<args.length; i++)
            if (args[i].toLowerCase().startsWith("-waittime="))
                WAITTIME = Integer.parseInt(args[i].substring(10));
            else if (!args[i].startsWith("-"))
                THREADS = Integer.parseInt(args[i]);
            else
                throw new IllegalArgumentException(
                        "unknown keyword: " + args[i]);

        buzz = new Buzz [ THREADS ];
        for (int t=0; t<buzz.length; t++) {
            buzz[t] = new Buzz();
            buzz[t].setDaemon(true);
            buzz[t].start();
        };

        Guard guard = new Guard();
        guard.setDaemon(true);
        guard.start();

        new PressBuzz().start();
        try {
            synchronized (buzz) {
                buzz.wait();
            }
        } catch (InterruptedException exception) {
            error("# run(args[]): " + exception);
        }
    }

    private class PressBuzz extends Thread {
        public void run() {
            synchronized (buzz) {
                buzz.notify();
            };
            for (int t=0; t<buzz.length; t++)
                buzz[t].suspend();
        }
    }

    private Buzz buzz[] = null;

    private class Buzz extends Thread {
        public void run() {
            while (true)
                yield();
        }
    }

    private class Guard extends Thread {
        public void run() {
            try {
                sleep(WAITTIME * 60000L);
            } catch (InterruptedException exception) {
                error("# Guard.run(): " + exception);
            };
            error("TEST FAILED: time exceeded (" + WAITTIME + " min)");
        }
    }

    private synchronized void error(String message) {
        System.out.println(message);
        Runtime.getRuntime().halt(1);
    }
}
