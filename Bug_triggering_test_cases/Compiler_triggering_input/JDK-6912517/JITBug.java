/**
 * Highlights a bug with the JIT compiler.
 * @author Matt Bruce m b r u c e __\at/__ g m a i l DOT c o m
 */
public class JITBug implements Runnable
{
    private final Thread myThread;
    private Thread myInitialThread;
    private boolean myShouldCheckThreads;

    /**
     * Sets up the running thread, and starts it.
     */
    public JITBug(int id)
    {
        myThread = new Thread(this);
        myThread.setName("Runner: " + id);
        myThread.start();
        myShouldCheckThreads = false;
    }

    /**
     * @param shouldCheckThreads the shouldCheckThreads to set
     */
    public void setShouldCheckThreads(boolean shouldCheckThreads)
    {
        myShouldCheckThreads = shouldCheckThreads;
    }

    /**
     * Starts up the two threads with enough delay between them for JIT to
     * kick in.
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException
    {
        // let this run for a bit, so the "run" below is JITTed.
        for (int id = 0; id < 20; id++) {
            System.out.println("Starting thread: " + id);
            JITBug bug = new JITBug(id);
            bug.setShouldCheckThreads(true);
            Thread.sleep(2500);
        }
    }

    /**
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run()
    {
        long runNumber = 0;
        while (true) {
            // run hot for a little while, give JIT time to kick in to this loop.
            // then run less hot.
            if (runNumber > 15000) {
                try {
                    Thread.sleep(5);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            runNumber++;
            ensureProperCallingThread();
        }
    }

    private void ensureProperCallingThread()
    {
        // this should never be null. but with the JIT bug, it will be.
        // JIT BUG IS HERE ==>>>>>
        if (myShouldCheckThreads) {
            if (myInitialThread == null) {
                myInitialThread = Thread.currentThread();
            }
            else if (myInitialThread != Thread.currentThread()) {
                System.out.println("Not working: " + myInitialThread.getName());
            }
        }
    }
}