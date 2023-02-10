
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class JitBugMain {

    public static void main(String[] args) throws InterruptedException {
        List<Thread> all = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            all.add(runInThread(new Consumer<List<Runnable>>() {
                @Override
                public void accept(List<Runnable> runnables) {
                    for (Runnable runnable : runnables) {
                        runnable.run();
                    }
                }
            }));
        }


        for (Thread thread : all) {
            thread.start();
        }

        for (int i = 0; i < all.size(); i++) {
            all.get(i).join();
            System.out.println("Thread complete: " + i);
        }
    }

    public static Thread runInThread(Consumer<List<Runnable>> consumer) throws InterruptedException {
        Thread runner = new Thread() {
            @Override
            public void run() {
                final NonBlockingSwap swap = new NonBlockingSwap(true);
                new Thread() {
                    {
                        setDaemon(true);
                    }

                    @Override
                    public void run() {
                        maybeSleep();
                        List<Runnable> arr = new ArrayList<>(10);
                        while (swap.isRunning()) {
                            arr = swap.swap(arr);
                            if (arr != null) {
                                consumer.accept(arr);
                                clear(arr);
                            }
                        }
                    }
                }.start();

                for (int i = 0; i < 100000; i++) {
                    String msg = i + "";
                    swap.put(() -> {
                        if (msg.equals("1000")) {
                            System.out.println("msg = " + msg);
                        }
                    });
                    maybeSleep();
                    if (i % 10000 == 0) {
                        System.out.println("i = " + i);
                    }
                }

                System.out.println("Done");
            }
        };
        runner.setDaemon(true);
        return runner;
    }

    public static void maybeSleep() {
        final double random = Math.random();
        if (random > .80) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void clear(List<Runnable> arr) {
        arr.clear();
    }

    public static class NonBlockingSwap {

        private volatile boolean running = true;
        private List<Runnable> queue = new ArrayList<>(10);
        private final Object lock = new Object();
        private final boolean yieldOnSpin;

        public NonBlockingSwap(boolean yieldOnSpin) {
            this.yieldOnSpin = yieldOnSpin;
        }

        public boolean isRunning() {
            return running;
        }

        public void setRunning(boolean isRunning) {
            this.running = isRunning;
        }

        public void put(Runnable r) {
            if (running) {
                synchronized (lock) {
                    queue.add(r);
                }
            }
        }

        public List<Runnable> swap(List<Runnable> buffer) {
            while (running) {
                synchronized (lock) {
                    if (queue.size() > 0) {
                        List<Runnable> toReturn = queue;
                        queue = buffer;
                        return toReturn;
                    }
                }
                if (yieldOnSpin)
                    Thread.yield();
            }
            return null;
        }
    }
}

