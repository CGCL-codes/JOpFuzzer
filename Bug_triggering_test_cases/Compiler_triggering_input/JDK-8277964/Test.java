public class Test {

    public static Test test(Object obj) {
        return (Test)obj;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100_000; ++i) {
            try {
                test(42);
            } catch (ClassCastException e) {
                if (e.getStackTrace().length == 0) {
                    throw new RuntimeException("Test failed");
                }
            }
        }
    }
}
