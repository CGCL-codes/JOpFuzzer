public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 100000; i += 1) {
            try {
                java.lang.reflect.Array.newInstance(void.class, 2);
            } catch (Throwable e) {
            }
        }
    }
}

