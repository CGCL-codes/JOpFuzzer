public class Test {
    private static final int ITERS = 10000000;
    public static void main(String args[]) {
        Test t = new Test();
        for (int i=0; i<10; i++) {
            test(t, 7);
        }
    }
    static Test test(Test t, int m) {
        int i = -(ITERS/2);
        if (i == 0) return null;
        Test v = null;
        while(i < ITERS) {
            if ((i&m) == 0) {
                v = t;
            }
            i++;
        }
        return v;
    }
}