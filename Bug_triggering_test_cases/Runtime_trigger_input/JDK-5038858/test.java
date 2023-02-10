
import java.lang.*;
import java.io.*;
import java.util.*;
                                                                                               
public class TesterOfDeath extends Thread implements Runnable
{
      public static int threadCounter = 4;
      public static void main (String[] args)
      {
          Thread tester1 = new TesterOfDeath();
          Thread tester2 = new TesterOfDeath();
          Thread tester3 = new TesterOfDeath();
          Thread tester4 = new TesterOfDeath();
          tester1.start();
          tester2.start();
          tester3.start();
          tester4.start();
                                                                                               
          int i = 0;
          while(threadCounter > 0)
          {             try
            {
              sleep(1);
            }
            catch (Exception e)
            {}
            System.out.println("Caller still running i = " + i);             i++;
          }
      }
      public void run()
      {
        for (int i = 0; i < 65000; i++)
        {
            System.out.println(i + " " + getName());
        }
        System.out.println("DONE! " + getName());
        threadCounter--;
    }
}

