public class ct extends Thread {
    static class ct0 extends ct {
        public void message() {
            // System.out.println("message");
        }

        public void run() {
            message();
            ct0 ct = (ct0) Thread.currentThread();
        }
    }
    static class ct1 extends ct0 {
        public void message() {
            // System.out.println("message");
        }
    }
    static class ct2 extends ct0 {
        public void message() {
            // System.out.println("message");
        }
    }
    static {
        new ct0();
        new ct1();
        new ct2();
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 100000; i++) {
            Thread t = null;
            switch (i % 3) {
                case 0: t = new ct0(); break;
                case 1: t = new ct1(); break;
                case 2: t = new ct2(); break;
            }
            t.start();
            t.join();
        }
    }
}