
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class HiddenLambda {

    public static void main(String[] args) {
        Thread myThread = new Thread(()->
        {
            foo(() -> bar());
        });
        myThread.start();
        Set<Thread> threads = Thread.getAllStackTraces().keySet();
        for (Thread thread : threads) {
            if (myThread == thread){
                System.out.println("----------From a different thread----------");
                List<StackTraceElement> stackTraceElements = Arrays.asList(thread.getStackTrace());
                for (StackTraceElement stackTraceElement : stackTraceElements) {
                    System.out.println(stackTraceElement);
                }
            }
        }
        try {
            myThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void foo(Runnable lambda) {
        lambda.run();
    }

    static void bar() {
        try {
            Thread.sleep(1000);
            System.out.println("----------From the same thread----------");
            List<StackTraceElement> stackTraceElements = Arrays.asList((new Exception()).getStackTrace());
            for (StackTraceElement stackTraceElement : stackTraceElements) {
                System.out.println(stackTraceElement);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
