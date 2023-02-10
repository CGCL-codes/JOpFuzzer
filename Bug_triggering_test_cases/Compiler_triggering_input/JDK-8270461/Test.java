public class Test {

    public static Object test(Object[] array) {
        return array.clone();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1_000_000; ++i) {
            test(new Object[2]);
        }
    }
}
