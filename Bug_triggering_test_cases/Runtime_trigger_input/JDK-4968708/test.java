
public class Main {
  public static void main(String[] args) {
    String s1 = "s1";
    int i = 0;

    while (true) {
      System.out.println(i++);
      s1 = s1.concat(s1);
      //s1 += s1;
    }
  }
}
