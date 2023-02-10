import java.nio.charset.StandardCharsets;
import java.util.Base64;

// Run with -Xbatch to be sure it gets C2 compiled.
public class Reproducer {
    static byte[] src;
    static byte[] dst;

    public static void main(String[] args) {
        String base64 = "VW14U1UySkdjRFpXYkdNeFZERmFjMWR1VGxoaVJuQldWbXhhUzJWV1ZrZFNWR3hSVlZRd09RPT0=";
        byte[] base = base64.getBytes();

        dst = new byte[15308];
        src = new byte[15308];
        for (int i = 0; i < 15308; i++) {
            if (i >= 15232) {
                src[i] = base[i - 15232]; // Make sure 'src' ends with 'base64' string
            } else {
                src[i] = 'A'; // Fill rest of 'src' with 'A'
            }
        }

        byte[] expectedArr = new byte[15308];
        Base64.getDecoder().decode(src, expectedArr);
        String expectedResult = new String(expectedArr, StandardCharsets.UTF_8); // Interpreter result

        for (int i = 0; i < 10000; i++) {
            // Warmup to C2 compile
            Base64.getDecoder().decode(src, dst);
        }

        dst = new byte[15308]; // clear
        Base64.getDecoder().decode(src, dst);
        String c2Result = new String(dst, StandardCharsets.UTF_8);
        if (!c2Result.equals(expectedResult)) {
            String s = "Non matching strings!" + System.lineSeparator()
                       + "Expected: " + expectedResult + System.lineSeparator()
                       + "Found:    " + c2Result;
            throw new RuntimeException(s);
        }
    }
}
