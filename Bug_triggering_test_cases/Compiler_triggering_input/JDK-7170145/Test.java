public class Test {
    volatile static private int a;
    static private int b;

    public static void main(String [] args) throws Exception {
        for (int i = 0; i < 100; i++) {
            new Thread() {

                @Override
                public void run() {
                    int tt = b; // makes the jvm cache the value of b

                    while (a==0) {

                    }

                    if (b == 0) {
                        System.out.println("error");
                    }
                }

            }.start();
        }

        b = 1;
        a = 1;
    }
}