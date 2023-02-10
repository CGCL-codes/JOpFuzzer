
class Main{
  static class A extends Main{}
  public static void main(String[] args){ m(f()); }
  static Main f(){ return new A(); }
  static void m(Main a){ System.out.println("FAIL"); }
  static void m(A a){ System.out.println("OK"); }
}
