
public class InterruptTest extends Thread
{
    public synchronized void run()
    {
        try
        {
            System.out.println("Calling wait(), isInterrupted(): " + isInterrupted());
            wait();
            System.out.println("wait() returned, isInterrupted(): " + isInterrupted());
        }
        catch(InterruptedException e)
        {
            System.out.println("InterruptedException caught");
        }
    }
    
    public static void main(String[] args) throws InterruptedException
    {
        InterruptTest test = new InterruptTest();

        System.out.println("Starting waiter");
        test.start();
        
        // wait for a while to allow the thread to start
        sleep(1000L);
        
        synchronized(test)
        {
            System.out.println("Calling test.interrupt()");
            test.interrupt();
            System.out.println("Calling test.notify()");
            test.notify();
            System.out.println("returned");
        }
    }
}
