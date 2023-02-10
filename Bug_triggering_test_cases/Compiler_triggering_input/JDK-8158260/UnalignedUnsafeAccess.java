import java.lang.reflect.Field;
import sun.misc.Unsafe;

public class UnalignedUnsafeAccess {

 static final int size = 128;
 static final int LENGTH = 17;
 static final long VALUE = 21;

 static final Unsafe unsafe;

 static {
  try {
   Field field = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
   field.setAccessible(true);
   unsafe = (sun.misc.Unsafe) field.get(null);
  } catch (Exception ex) {
   throw new IllegalStateException(ex);
  }
 }

 static class NativeCell {

  long peer;

  NativeCell() {
   peer = allocate();
  }

  void free() {
   UnalignedUnsafeAccess.free(peer);
  }

  public String path() {


   long offset = peer + VALUE + getInt(peer + LENGTH);

   // getInt(peer + LENGTH) can return a value that
   // after added to peer + VALUE yields an invalid addr.,
   // so check the new offset range:
   if (offset < peer) // out of range
    offset = peer + VALUE; // peer + VALUE is always valid.
   else if (offset > peer + size - 4) // out of range
    offset = peer + VALUE; // peer + VALUE is always valid.

   int size = unsafe.getInt(offset);

   return "path" + offset + ":" + size;
  }

 }

 public static int getInt(long address) {
  return unsafe.getInt(address);
 }

 public static long allocate() {
  return unsafe.allocateMemory(size);
 }

 public static void free(long addr) {
  unsafe.freeMemory(addr);
 }

 public static void main(String[] args) {

  for (long i = 0; i < 100000000; ++i) {

   NativeCell cell = new NativeCell();
   if (cell.path().startsWith("path"))
    System.out.print("."); // No JITed code executed yet.
   cell.free();
  }
 }
}