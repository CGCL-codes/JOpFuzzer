
public class Test
{
      private Thread thread = new Thread()
      {
            public void run()
            {
                  for( int i=0; i<10; i++ )
                        System.out.println("In thread.run() " + i);
            }
      };

      public Test()
      {
      }

      public void execute()
      {
            System.out.println("execute()");
            thread.start();

            try {
                  //wait for run() to exit
                  Thread.sleep(10*1000);
            }
            catch (InterruptedException ex) {
            }

            if( thread.isAlive()==false )
                  thread.start();

            try {
                  //wait
                  Thread.sleep(10*1000);
            }
            catch (InterruptedException ex) {
            }
      }

      public static void main(String[] args)
      {
            Test test = new Test();
            test.execute();
      }
}

