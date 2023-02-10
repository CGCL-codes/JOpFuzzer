public class Test {
  public static void main(String[] args) {
    int w = 64;
    int levels = (int)(Math.log(w) / Math.log(2.0)) + 1;
    System.out.println("Width " + w + " gives " +
      levels + " mipmap levels");
  }
}
