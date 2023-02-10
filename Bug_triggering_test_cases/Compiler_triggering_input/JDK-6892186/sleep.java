public class sleep {
    int iii;
    public static void main(String[] args) throws Exception {
        int ii = 0;
        for (int i = 0; i < 2000000; i++) {
            ii += test2();
        }
    }
    static int test2() throws Exception {
        return test();
    }
    static int test() throws Exception {
        sleep sl = new sleep();
        synchronized (sleep.class) {
            sleep.class.wait(1);
        }
        return sl.iii;
    }
}
