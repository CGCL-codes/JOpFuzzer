import java.lang.reflect.Field; 
import sun.misc.Unsafe; 

public class Chunk2 { 

   public static final Unsafe UNSAFE; 

   public final long baseAddress; 
   public final int length; 

    static { 
       Unsafe u = null; 
       try { 
          final Class<Unsafe> uc = Unsafe.class; 
          final Field field = uc.getDeclaredField("theUnsafe"); 
          field.setAccessible(true); 
          u = (Unsafe) field.get(uc); 
       } catch (NoSuchFieldException | IllegalAccessException | RuntimeException ex) { 
          ex.printStackTrace(); 
       } 
       UNSAFE = u; 
    } 

   public Chunk2(int length) { 
      baseAddress = UNSAFE.allocateMemory(length); 
      this.length = length; 
   } 

   public final long getBaseAddress() { 
      return baseAddress; 
   } 

   public final int getLength() { 
      return length; 
   } 

   protected Chunk2(Chunk2 oc, int op, int ol) { 
      baseAddress = oc.getBaseAddress() + op; 

      if (oc.getLength() == -1) { 
         length = -1; 
      } else { 
         length = Math.min(ol, oc.getLength() - op); 
      } 
   } 

   public Chunk2 slice(int p, int l) { 
      Chunk2 o = new Chunk2(this, p, l); 
      return o; 
   } 

   public final void fillSmall(int tp, int tl, byte b) { 
      long p = baseAddress + tp; 
      for (int i = 0; i < tl; i++) { 
         UNSAFE.putByte(p++, b); 
      } 
   } 

   public void put_Long_DU(int p, int d, long v) { 
      long a = baseAddress + p; 

      for (int i = d - 1; i >= 0; i--) { 
         UNSAFE.putByte(a + i, (byte)v); 
         v /= 10; 
      } 
   } 

   public long get_DU_Long(int p, int l) { 
      long a = baseAddress + p; 

      long ret = 0; 
      for (int i = 0; i < l; i++) { 
         final byte b = UNSAFE.getByte(a++); 
         ret += b & 0xff; 
      } 

      return ret; 
   } 

   public byte[] getAsByteArray(int p, int l) { 
      byte[] ret = new byte[l]; 
      long a = baseAddress + p; 
      for (int i = 0; i < l; i++) { 
         ret[i] = UNSAFE.getByte(a++); 
      } 
      return ret; 
   } 

} 
