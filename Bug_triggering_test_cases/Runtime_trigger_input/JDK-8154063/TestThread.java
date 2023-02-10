
public class TestThread
{

    public static void main(String[] args)
    {

        try
        {
            Thread.sleep(15000);
        }
        catch (InterruptedException e)
        {
        }

        for (int i = 0; i < 3000; i++)
        {
            MyThread thd = new MyThread();
            thd.setName("DummyThd-"+Integer.toString(i));
            thd.start();
        }

        System.out.println("Sleeping for 15 secs");
        try
        {
            Thread.sleep(15000);
        }
        catch (InterruptedException e)
        {
        }

        System.gc();
        System.out.println("GC Done");

        System.out.println("Current Active Thread Count " + Thread.activeCount());

        Thread thd = new JustKeepRunning();
        thd.start();
        try
        {
            thd.join();
        }
        catch (InterruptedException e)
        {
        }

    }
}

class MyThread extends Thread
{

    @Override
    public void run()
    {
        try
        {
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getName() + " Done!");
        }
        catch (InterruptedException e)
        {
            // do nothing
        }
    }
}

class JustKeepRunning extends Thread
{

    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                Thread.sleep(500000);
                System.out.println(Thread.currentThread().getName() + " Done!");
            }
            catch (InterruptedException e)
            {
                // do nothing
            }
        }
    }
}

