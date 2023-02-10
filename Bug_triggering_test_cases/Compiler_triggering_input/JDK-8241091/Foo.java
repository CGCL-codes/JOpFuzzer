public class Foo {

    private static int bar(int x) {
        return Integer.bitCount(x);
    }

    public static void main(String[] args) {
        int sum = 0;
        for (int i = 0; i < 30000; i++) {
            sum += bar(i);
        }
        System.out.println(sum);
    }
}