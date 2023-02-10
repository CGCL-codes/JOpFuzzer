class A {
    public static void main(String argv[]) {

        int size = 4000000;
        int x[] = new int[size];

        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < size; j += 8192) {
                int y = x[j];
            }
        }
    }
}