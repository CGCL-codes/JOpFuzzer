import java.util.ArrayList;
import java.util.List;

public class MemTest {
    public static void main(final String[] args) {
        final List<byte[]> segments = new ArrayList<byte[]>(64000);

        final int size = 6400000;

        for (int i = 0; i < 1000000000; i++) {
            segments.add(new byte[size * i]);
        }

        for (final byte[] data : segments) {
        }
    }
}
