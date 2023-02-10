class Count {
    static int count = 0;
    static int acc = 1;
    public static void main(String[] args) {
        boolean cond = false;
        for (int i = 0; i < 10; i++) {
            switch (i % 3) {
            case 0:
                System.out.println("count: " + count);
                for (int j = 0; j < 100; j++) {
                    for (int k = 0; k < 100; k++) {
                        count = acc;
                    }
                    if (cond) {
                        break;
                    }
                }
                acc++;
                break;
            case 1:
                break;
            case 2:
                break;
            }
        }
    }
}
