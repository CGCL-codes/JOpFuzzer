import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class C {

    public static void m(String s) {
        Pattern pattern = Pattern.compile("");
        Matcher matcher = pattern.matcher(s);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10_000; ++i) {
            try {
                m(null);
            } catch (Throwable e) {
            }
        }
    }
}