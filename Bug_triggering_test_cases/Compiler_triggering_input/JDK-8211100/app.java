public class app {
    public static void main(String[] args) {
        Integer cnt = 100;
        if (args.length > 0) {
            cnt = Integer.parseInt(args[0]);
        }

        for (int i = 0; i < cnt; i++) {
            test(4558828911L,
                    4294967296L);
        }
    }

    private static void test(long one, long two) {
        while (true) {
            if (one >= two) {
                break;
            }
        }
    }
}