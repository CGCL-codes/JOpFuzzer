public class Test {

    public static void doCall(int claims) {
        if (claims == 0) return;
        doCall(claims - 1);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            doCall(i);
        }
    }
}