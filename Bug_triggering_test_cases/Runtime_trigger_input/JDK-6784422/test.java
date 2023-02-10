
package test;

import java.util.ArrayList;

public class ConsumeThreads
{
    public static void main(String[] args)
    {
        ArrayList list = new ArrayList();
        
        while (true)
        {
            Thread thread = new MyThread();
            thread.start();
            list.add(thread);
        }
    }
    
    static class MyThread
    extends Thread
    {
        public void run()
        {
            synchronized(this)
            {
                try
                {
                    this.wait();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

}
