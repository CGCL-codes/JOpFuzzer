package test;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ThreadFactory;

/**
 * Named thread factory for use with the Executors
 * <li>Example Usage:
 * 	<ul>Executors.newFixedThreadPool(nThreads, threadFactory)</ul>
 */
public class NamedThreadFactory implements ThreadFactory,UncaughtExceptionHandler {

    private String name;

    private ThreadGroup threadGroup;

    private boolean daemon;

    private final int threadPriority = Thread.NORM_PRIORITY;

    public NamedThreadFactory(String name) {
        this(name, null, false);
    }

    /**
     * Create a threadFactory
     *
     * @param name the name of the thread
     * @param threadGroup the threadGroup for the threads
     * @param daemon whether the threads within the threadFactory are daemon threads or not
     */
    public NamedThreadFactory(String name, String threadGroup, boolean daemon) {
        this.name = name;
        this.threadGroup = new ThreadGroup(threadGroup);
        this.daemon = daemon;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(threadGroup, runnable, name);
        thread.setPriority(threadPriority);
        thread.setDaemon(daemon);
        thread.setUncaughtExceptionHandler(this);
        return thread;
    }
    public void uncaughtException(Thread thread, Throwable throwable) {
        System.out.println("Uncaught Exception in thread "  + thread.getName() );
    }

}