
class Test {
    public static void main(String[] args)
            throws InterruptedException
    {
        Thread t = new Thread(() -> {
            while (true) {
                new Thread(() -> {
                    try {
                        Thread.currentThread().join();
                    }
                    catch (InterruptedException e) {
                    }
                }).start();
            }
        });

        t.start();
        Thread.currentThread().join();
    }
}
