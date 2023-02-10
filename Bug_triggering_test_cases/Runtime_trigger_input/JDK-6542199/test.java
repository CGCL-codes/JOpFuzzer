
public class MemoryLeak
{
  public void dump()
  {
    System.out.println("hello there");
  }

  public static void main(String[] args)
  {
    int count;
    
    System.out.println("NullPointerException ...");
    count = 0;
    for (int runs=0; runs<60; runs++)
    {
      for (int iter=0; iter<10000; iter++)
      {
        try
        {
          MemoryLeak m = null;
          m.dump();
        }
        catch(NullPointerException npe)
        {
          count++;
        }
      }

      try{Thread.sleep(1000);}catch(InterruptedException ie){}
    }
    System.out.println(count + " NullPointerExceptions");

    System.out.println("ClassCastException ...");
    count = 0;
    for (int runs=0; runs<60; runs++)
    {
      for (int iter=0; iter<10000; iter++)
      {
        try
        {
          Object o = "hello there";
          ((MemoryLeak)o).dump();
        }
        catch(ClassCastException cce)
        {
          count++;
        }
      }

      try{Thread.sleep(1000);}catch(InterruptedException ie){}
    }
    System.out.println(count + " ClassCastExceptions");

    System.out.println("done, bye bye");
  }
}

