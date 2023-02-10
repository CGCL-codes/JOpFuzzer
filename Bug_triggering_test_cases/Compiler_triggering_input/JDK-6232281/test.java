public class test {
    static int test(int l, int limit) {
        long j = 0;
        for (int i = 0; i < limit; i++) {
            j += l;
        }
        return (int)j;
    }

    public static void main(String[] args) {
        int i = test(5, 30000);
    }
}