public class test{
    public static void main(String[] args) throws Exception {
        AtomicBoolean stop = new AtomicBoolean();
        AtomicInteger count = new AtomicInteger();

        new Thread(() -> {
            while (!stop.get()) {
                Runtime.getRuntime().availableProcessors();
                count.incrementAndGet();
            }
        }).start();

        try {
            int lastCount = 0;
            while (true) {
                Thread.sleep(1000);
                int thisCount = count.get();
                System.out.printf("%s calls/sec%n", thisCount - lastCount);
                lastCount = thisCount;
            }
        }
        finally {
            stop.set(true);
        }
    }
}