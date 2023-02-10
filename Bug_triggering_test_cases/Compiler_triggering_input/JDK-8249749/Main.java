import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        int n = 4096;
        long[] data = new long[n * n];

        for (int k = 0; k < n; k++) {
            IntStream.range(0, n).forEach(j -> {
                for (int i = 0; i < n; i++)
                    data[j * n + i]++;
            });
        }
    }
}
