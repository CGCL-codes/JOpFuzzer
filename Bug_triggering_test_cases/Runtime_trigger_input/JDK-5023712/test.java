
public strictfp class TestCase
{
    private static double mostRecentInner;

    public static void main(String[] argv)
        throws Exception
    {
        System.out.println("(MAIN) HERE: " + StrictMath.exp(1.0));

        // start a new thread...
        MyThread th = new MyThread();
        th.start();

        // let it really start up...
        Thread.sleep(1000);

        // make it break!
        System.out.println("(MAIN) BEFORE NOTIFY: " + StrictMath.exp(1.0));
        synchronized (th._lock)
        {
            th._lock.notifyAll();
        }
        System.out.println("(MAIN) AFTER NOTIFY: " + StrictMath.exp(1.0));


        // let it go a few more times...
        Thread.sleep(1000);

        // done!
        System.out.println("(MAIN) DOWN HERE: " + StrictMath.exp(1.0));

        System.out.println();
        if (mostRecentInner != StrictMath.exp(1.0))
        {
            System.out.println("BAD NEWS: two threads do NOT compute the same "
                + "value for StrictMath.exp(1.0).");
        }
        else
        {
            System.out.println("GOOD NEWS: values from both threads seem to "
                + "match.");
        }
        System.exit(0);
    }

    private static strictfp class MyThread extends Thread
    {
        Object _lock = new Object();

        public void run()
        {
            try
            {
                while (true)
                {
                    System.out.println("(MT) TOP OF LOOP: "
                        + StrictMath.exp(1.0));
                    try
                    {
                        synchronized (_lock)
                        {
                            _lock.wait(300);
                        }
                    }
                    catch (InterruptedException e)
                    {
                        // ignore
                    }
                    mostRecentInner = StrictMath.exp(1.0);
                    System.out.println("(MT) BOTTOM OF LOOP: "
                        + StrictMath.exp(1.0));
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
