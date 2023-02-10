import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Random;

public class ShouldWork {
    public static void main(String args[]) throws Exception {
        // NOTE: deterministic seed, yet fails a different way each time (compile issue)
        Random random = new Random(0L);
        for (int i = 0; i < 1000000; i++) {
            String expected = randomString(random);
            Reader reader = new StringReader(expected);
            StringBuilder sb = new StringBuilder();
            int ch = 0;
            while ((ch = readChar(random, reader)) >= 0) {
                sb.append((char) ch);
            }
            String actual = sb.toString();
            if (!expected.equals(actual)) {
                throw new AssertionError("expected=" + expected + ", actual=" + actual);
            }
        }
    }

    // reads a single character with read(char[])
    static int readChar(Random random, Reader input) throws IOException {
        char c[] = new char[1];
        int ret = input.read(c);
        assert ret != 0;
        return ret < 0 ? ret : c[0];
    }

    static String randomString(Random random) {
        int length = random.nextInt(10);
        char chars[] = new char[length];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (random.nextInt(26) + 'a');
        }
        return new String(chars);
    }
}