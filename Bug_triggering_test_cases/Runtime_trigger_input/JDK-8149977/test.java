import java.util.*;

public class LambdaTest {
  public static void main(String args[]) {
    List<String> a = new ArrayList<>();
    a.add("hello world.");
    a.forEach(System.out::println);
  }
}