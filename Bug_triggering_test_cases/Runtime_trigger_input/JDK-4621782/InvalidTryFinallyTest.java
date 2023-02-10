
public class InvalidTryFinallyTest
{
  public static void
  main(	String[] args)
  {
    int result = runTest();
    System.err.println("Result = " + result);
  }

  public static int
  runTest()
  {
    try
    {
      try
      {
        return 1;
      }
      finally
      {
        try
        {
          return 2;
        }
        catch (RuntimeException re)
        {
        }
      }
    }
    finally
    {
    }
  }
}

