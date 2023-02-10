public class RecursiveCall {
    static int depth;

    public static void main(String[] args) {
        try {
            recursive();
        } catch (StackOverflowError e) {
            System.out.println(depth);
        }
    }

    static void recursive() {
        depth++;
        recursive();
    }
}