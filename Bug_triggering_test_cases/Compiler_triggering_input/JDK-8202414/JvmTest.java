import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;

public class JvmTest {

  public static void main(String[] args) {
    System.err.close();
    int count = 0;
    while (count++ < 120000) {
      test();
    }
  }

  public static void test() {
    byte[] newBuf = serBytes(397);

    if (newBuf.length != 397) {
      System.out.println("array length internal error, expected: " +
              397 + " (0x" + Integer.toHexString(397) + ")"
              + " actual: " + newBuf.length
              + " (0x" + Integer.toHexString(newBuf.length) + ")");
    }
  }

  public static byte[] serBytes(int bufLen) {
    byte[] buf = new byte[bufLen];
    THE_UNSAFE.putInt(buf, BYTE_ARRAY_BASE_OFFSET + 1, buf.length);
    System.err.println("ref " + buf);
    return buf;
  }


  /*
    Unsafe fields and initialization
   */
  static final Unsafe THE_UNSAFE;
  static final long BYTE_ARRAY_BASE_OFFSET;
  static {
    THE_UNSAFE = (Unsafe) AccessController.doPrivileged(
            new PrivilegedAction<Object>() {
              @Override
              public Object run() {
                try {
                  Field f = Unsafe.class.getDeclaredField("theUnsafe");
                  f.setAccessible(true);
                  return f.get(null);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                  throw new Error();
                }
              }
            }
    );
    BYTE_ARRAY_BASE_OFFSET = THE_UNSAFE.arrayBaseOffset(byte[].class);
  }
} 

