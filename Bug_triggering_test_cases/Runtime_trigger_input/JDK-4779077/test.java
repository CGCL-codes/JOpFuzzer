
class GCTest {
  public static void main(String[] args) {
    Obj test = new Obj(1000000000);
   }
  private static class Obj {
    private byte[] data;
    public Obj(int size) {
      data = new byte[size];
    }
  }
}
