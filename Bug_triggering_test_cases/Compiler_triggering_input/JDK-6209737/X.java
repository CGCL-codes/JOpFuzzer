public class X {
    private static int COUNT = 1000000000;

    public static void main(String[] args) {
        System.out.println("starting");
        long start = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) { }
        System.out.println(System.currentTimeMillis() - start);
    }
}