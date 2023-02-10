class Chair
{
  static boolean gcrun = false;
  static boolean f = false;
  static int created = 0;
  static int finalized = 0;
  int i;

  Chair()
  {
    i = ++ created;
        if((created % 10000) == 0)
          System.out.println("Created " + created);
  }

  protected void finalize()
  {
    if(!gcrun)
        {
      gcrun = true;
          System.out.println("Beginning to finalize after " + created +
                                                  " Chairs have been created");
        }
        if(i == 50000)
        {
      System.out.println("Finalizing Chair #300000, " + "Setting flag to" +
                                                  " stop Chair creation");
          f = true;
        }
        finalized ++;

        if(finalized >= created)
          System.out.println("All " + finalized + " finalized");
  }
}

public class Garbage1
{
  public static void main(String [] args)
  {
        int count = 1;
        long t1 = System.currentTimeMillis();
    if (args.length == 0)
        {
      System.err.println("Usage: \n" + "java Garbage1 before\n or:\n" +
                                                  "java Garbage1 after");
      return;
        }

        while(!Chair.f)
        {
      new Chair();
          new String("To take up space");
        // if (count++ % 50000 == 0)
                // System.gc();
        }

        System.out.println("After all Chairs have been created:\n" +
        "total created = " + Chair.created +
        ", total finalized = " + Chair.finalized);

        if(args[0].equals("before"))
        {
      System.out.println("gc():");
          System.gc();
          System.out.println("runFinalization():");
          System.runFinalization();
        }
        System.out.println("bye!");
        if(args[0].equals("after"))
          System.runFinalizersOnExit(true);

        long t2 = System.currentTimeMillis();
        System.out.println("Finish: " + (t2-t1) + "ms" );
  }
}