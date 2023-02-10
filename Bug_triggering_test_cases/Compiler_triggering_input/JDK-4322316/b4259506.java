import java.io.PrintStream;

public class b4259506 {

    public static void main(String argv[]) {
System.exit(run(argv, System.out) + 95/*STATUS_TEMP*/);
    }

    public static int run(String argv[], PrintStream out) {
Thread t1 = new Thread(new b4259506_inf());
t1.start();
t1.stop();
return 0/*STATUS_PASSED*/;
    }
}

class b4259506_inf implements Runnable {
    public void run() {
Thread.yield();
while(true) {
}
    }
}