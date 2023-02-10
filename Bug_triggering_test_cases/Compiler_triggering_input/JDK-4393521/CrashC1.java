public class CrashC1 {
    public long alignUp(long size, long alignment) {
      return (size + alignment - 1) & ~(alignment - 1);
    }
  
    public static void main(String[] args) {
      new CrashC1().alignUp(8, 8);
    }
  }
  