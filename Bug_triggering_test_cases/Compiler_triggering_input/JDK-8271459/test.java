public class test {

    static String m() {
        StringBuilder sb = new StringBuilder(-1);
        return sb.toString();
    }

    public static void main(String[] args) {
        int sum = 0;
        for (int i = 0; i < 10_000; ++i) {
            try {
                m();
            } catch (Throwable e) {
                sum += 1;
            }
        }
        System.out.println(sum); // should be 10_000
    }
}