
import java.nio.charset.Charset;

public class JvmCrashTest {

  public static void main(String[] args) {
    String test = "Ãgouts";
    String converted = new String(test.getBytes(), Charset.forName("ISO-8859-1"));
    System.out.printf("%s -> %s%n", test, converted);
  }

}

