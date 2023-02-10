
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPool {
  public static void main(String[] args) {
    int n = Integer.parseInt(args[0]);
    final AtomicInteger todo = new AtomicInteger(2*n);

    ExecutorService p = Executors.newFixedThreadPool(n);
    for(int i=0; i<2*n; i++) {
      p.execute(new Runnable() { public void run() {
	System.out.println(todo.decrementAndGet());
      }});
    }
  }
}

