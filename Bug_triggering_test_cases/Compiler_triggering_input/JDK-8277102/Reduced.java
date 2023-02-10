public class Reduced {
    static boolean arr[] = new boolean[1024];

    static int foo(long l) {
        Boolean b = true;
        int k = b.hashCode();
        return k;
    }

    public static void main(String[] args) throws Exception {
        int k = 0;
        for (int i = 0; i < 600000; i++) {
            k +=foo(5);
        }
        System.out.println(k);
    }
}