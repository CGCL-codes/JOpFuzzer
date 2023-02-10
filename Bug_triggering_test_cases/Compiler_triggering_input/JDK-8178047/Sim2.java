public class Sim2 { 

   private final Chunk c_R = new Chunk(64); 

   public static void main(String[] args) { 
      Sim2 s = new Sim2(); 
      s.go();
   } 

   private void go() { 
      for (long i = 1; i < 198765L; i++) { 
          long p = c_R.baseAddress + 9; 

          c_R.UNSAFE.putByte(p++, (byte)0);
          c_R.UNSAFE.putByte(p++, (byte)0);
          c_R.UNSAFE.putByte(p++, (byte)0);
          c_R.UNSAFE.putByte(p++, (byte)0);
          c_R.UNSAFE.putByte(p++, (byte)0);
          c_R.UNSAFE.putByte(p++, (byte)0);
          c_R.UNSAFE.putByte(p++, (byte)0);
          c_R.UNSAFE.putByte(p++, (byte)0);
          c_R.UNSAFE.putByte(p++, (byte)0);
          c_R.UNSAFE.putByte(p++, (byte)0);

          //   c_R.put_Long_DU(9, 10, i); 
          long a = c_R.baseAddress + 9; 
          long v = i;
          for (int j = 10 - 1; j >= 0; j--) { 
             c_R.UNSAFE.putByte(a + j, (byte)v); 
             v /= 10; 
          } 


         long l = c_R.get_DU_Long(9, 10);

         System.out.println(i);
        // System.out.println(l); // Execute with -Xbatch if this is removed
         if (l == 0) {
           long l2 = c_R.get_DU_Long(9, 10);
           System.out.println("Value: " + l2 + " Content: ");
           printR();
           throw new RuntimeException("FAIL " + i);
         }
      }
   }

   private void printR() { 
      final byte[] ba = c_R.getAsByteArray(9, 10); 
      for (int i = 0; i < ba.length; ++i) {
        System.out.println((int)ba[i]);
      } 
   } 
} 


