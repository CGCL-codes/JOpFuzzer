
import java.util.*;
import junit.framework.TestCase;
import junit.textui.TestRunner;

public class IterableInterfaceBugTest extends TestCase
{
  public static void main(String[] args)
  {
    System.out.println("JRE " + System.getProperty("java.runtime.version"));
    TestRunner.run(IterableInterfaceBugTest.class);
  }

  public interface MyIterable extends Iterable<String>
  {
    MyIterable INSTANCE = MyIterableImpl.INSTANCE;
  }

  public static class MyIterableImpl implements MyIterable
  {
    public static final MyIterableImpl INSTANCE = new MyIterableImpl();

    public Iterator<String> iterator()
    {
      return Arrays.asList("Hello", "World.").iterator();
    }
  }

  public interface NotIterable
  {
    NotIterable INSTANCE = NotIterableImpl.INSTANCE;
  }

  public static class NotIterableImpl implements NotIterable
  {
    public static final NotIterableImpl INSTANCE = new NotIterableImpl();

    public Iterator<String> iterator()
    {
      return Arrays.asList("Hello", "World.").iterator();
    }
  }

  public void testNotIterable()
  {
    // succeeds on all JREs (problem only seen if the interface extends Iterable)
    assertNotNull(NotIterableImpl.INSTANCE);
    assertNotNull(NotIterable.INSTANCE);
  }

  public void testMyIterable()
  {
    assertNotNull(MyIterableImpl.INSTANCE);
    // fails for JRE 1.8.0_25 and 1.8.0_31
    // succeeds on JRE 1.7.x and 1.8.0_40.
    assertNotNull(MyIterable.INSTANCE);
  }
}

