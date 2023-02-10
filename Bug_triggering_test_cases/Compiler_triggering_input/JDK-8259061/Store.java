class Store {
    static int out;
    public static void main(String[] strArr) {
        System.out.println();
        boolean cond = false;
        int i = 0;
        while (i < 100) {
            int j = 0;
            while (j < 100) {
                out = 42;
                if (cond) {
                    break;
                }
                int k = 0;
                while (k < 2) {
                    k++;
                }
                j++;
            }
            i++;
        }
    }
}
