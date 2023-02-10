public class testme {

    public static void runme() {
      int i = 0;
      TripleThreadStop.cStarted = true;
      while (true) {
        try {
          while (true) {
            ++i;
          }
        }
        catch (ThreadDeath e) {
        }
      }
    }

    public static void main(String s[]) {
      runme();
    }
}
