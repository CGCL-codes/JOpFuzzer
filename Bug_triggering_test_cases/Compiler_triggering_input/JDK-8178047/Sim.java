public class Sim { 
   private final Chunk c_A1; 
   private final Chunk c_A2; 
   private final Chunk c_R; 

   public static void main(String[] args) { 
      System.out.println("main() >"); 

      Sim s = new Sim(); 
      s.go(); 

      System.out.println("main() <"); 
   } 

   public Sim() { 
      Chunk r_0 = new Chunk(64); 
      c_A1 = r_0.slice(0, 11); 
      c_A2 = r_0.slice(11, 11); 
      c_R = r_0.slice(22, 19); 
   } 

   private void go() { 
      c_A1.fillSmall(0, 11, (byte)0x30); 
      for (long i = 0; i < 198765L; i++) { 
         c_A2.put_Long_DU(0, 11, i ); 
         c_R.fillSmall(9, 10, (byte)0x30); 
         c_R.put_Long_DU(9, 10, c_R.get_DU_Long(9, 10) + c_A2.get_DU_Long(0, 11)); 
         c_R.put_Long_DU(9, 10, c_R.get_DU_Long(9, 10) + c_A1.get_DU_Long(0, 11)); 

         printR(); 
      } 
   } 

   private void printR() { 
      final byte[] ba = c_R.getAsByteArray(9, 10); 
      final String s = new String(ba); 
      System.out.println("[" + s + "]"); 
   } 
} 


