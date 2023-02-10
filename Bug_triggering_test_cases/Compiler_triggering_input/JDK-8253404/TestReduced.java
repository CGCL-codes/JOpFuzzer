public class TestReduced {
    public static void main(String[] args) {
        long out = 0;
        // The trip count needs to be >= 2.
        for (int i = 0; i < 2; i++) {
            int foo = i;
            // The trip count needs to be >= 17.
            for (int j = 0; j < 17; j++) {
                // This needs to be an addition.
                foo = foo + foo;
            }
            out = foo;
        }
        System.out.println(out);
    }
}
