

import java.util.*;

class Test1 {


   public static void main(String[] args) {
      Thread t = new Thread(new Run());
      t.start();
      
      Thread t2 = new Thread(new Junk());
      t2.start();
   }
   
   private static class Junk implements Runnable {
      public void run() {
         while ( true ) {
            new Integer(1);
         }
      }
   }

   private static class Run implements Runnable {
      public void run() {
         long t1 = System.currentTimeMillis();
         for (int j=0; j < 10000; j++) {
            for (int k=0; k < 1000000; k++) {
            }
         }
         long t2 = System.currentTimeMillis();
         System.out.println("Done: " + (t2-t1));
      }
   }

}
