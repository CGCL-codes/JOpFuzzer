final class UninitializedFinals {
    static UninitializedFinals global = new UninitializedFinals(1);
    final int x;
    final int y;
    final int z;

    static volatile boolean flag = true;
    UninitializedFinals(int w) {
        x = w + w*w;
        y = w + w*w + w*w*w;
        z = w + w*w + w*w*w + w*w*w*w + w*w*w*w*w;
    }

    static class Writer extends Thread {
        public void run() {
            for(int i = 100*1000*1000; i > 0; i--)
                global = new UninitializedFinals((int)i);
            global = new UninitializedFinals(-1);
            flag = false;
        };
    };

    static void read() {
        int k = 0;
        for (;;) {
            ++k;
            int x = global.x;
            if (x == 0)
                throw new IllegalStateException("saw uninitialized global.x = 0");
            else if (!flag && x < 0)
                return;
        }
    }

    public static void main(String args[]) {
        long start = System.currentTimeMillis();
        new Writer().start();
        read();
        long end = System.currentTimeMillis();
        System.out.println("First pass done");
        System.out.println("Time taken: " + (end-start));
        start = System.currentTimeMillis();
        global = new UninitializedFinals(1);
        flag = true;
        new Writer().start();
        read();
        end = System.currentTimeMillis();
        System.out.println("Second pass done");
        System.out.println("Time taken: " + (end-start));
    }

}
