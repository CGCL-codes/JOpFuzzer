public class Bug {
    private static int SIZE = 60000;
    private static char[] a = new char[SIZE];
    private static char[] b = new char[SIZE];

    public static void main(String[] args) {
        for (int i = 0; i < SIZE; i++) {
            a[i] = b[i] = (char) i;
        }
        for (int i = 0; i < SIZE; i++) {
            a[i] = (char) Math.abs(a[i]);
        }
        for (int i = 0; i < SIZE; i++) {
            if (a[i] != b[i]) {
                throw new RuntimeException("Broken!");
            }
        }
        System.out.println("OK");
    }
}