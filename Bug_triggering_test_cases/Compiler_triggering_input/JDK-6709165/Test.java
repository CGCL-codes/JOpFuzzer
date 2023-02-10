public class Test {
    public static void main(String[] args) throws Exception {
        Executor executor = Executors.newFixedThreadPool(1,
                new ThreadFactory() {
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(r);
                        t.setDaemon(true);
                        return t;
                    }
                });

        int i = 0;
        while (i<10000) {
            final CountDownLatch latch = new CountDownLatch(1);
            executor.execute(new Runnable() {
                public void run() {
                    latch.countDown();
                }
            });
            latch.await();
            i++;
        }
    }
}
