
public class Test
{
  public static void main(String[] args)
  {
    long start = System.currentTimeMillis();
    for(int i = 0; i < 100; i++) {
      try {
        Thread.sleep(100);
      } catch( InterruptedException e) {}
  }
  long stop = System.currentTimeMillis();
  System.out.println("Time: " + (stop - start));
  }
}
