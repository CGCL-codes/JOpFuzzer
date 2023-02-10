public class Test {
  public static synchronized int hash(Object o) { return o.hashCode(); }
  public static void main(String[] args) throws Exception {
    int sum = 0;
    for (int i = 0; i < 30000; i++) {
      sum += hash(i);
      Thread.sleep(1);
    }
    sum += hash("Shanghai");
    System.out.println(sum);
  }
}