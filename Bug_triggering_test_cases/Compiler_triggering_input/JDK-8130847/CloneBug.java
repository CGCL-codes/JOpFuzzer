import java.io.IOException;
import java.time.ZoneId;
import java.util.TimeZone;

public class CloneBug {

    static final TimeZone UTC = TimeZone.getTimeZone("UTC");

    static void test() {
        TimeZone.setDefault(UTC);
        for (int i = 0; i < 16801; i++) {
            ZoneId.systemDefault();
        }
    }

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 100; i++) {
            test();
        }
    }
}