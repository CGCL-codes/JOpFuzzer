public class BadShift {

    synchronized static long inc(int a) { return (long)(a+1); }
    static long run_test(long a) {
      return (a << 75) + inc(75);
    }
  
    public static void main(String s[]) {
      System.out.println(run_test(12L));
    }
  }