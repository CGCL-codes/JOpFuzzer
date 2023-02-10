
import jdk.internal.misc.Unsafe;
public class UnsafeCrash {
  public static void main(String[] args) throws Throwable {
    Unsafe.getUnsafe().putInt(0L, 0);
  }
}
