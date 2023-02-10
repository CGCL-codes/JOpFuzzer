
import java.util.*; 
public class Test0 { 
   private static int[] staticIntArray = new int[1_000]; 
    
   static { 
      /* Change isMethodCall to false so that staticIntArray access is direct rather than via a method 
      * the the Java 9 performance issue disappears. */ 
      boolean isMethodCall = true; 
      Date d = new Date(); 
      for(int i=0; i < 100_000; i++) { 
         if (isMethodCall) { 
            process(0); 
         } 
         else { 
            int valueIn = 0; 
            for(int j=0; j < 1000; j++) { 
               int wrkInt = valueIn & staticIntArray[j]; 
            } 
         } 
      } 
      long difference = new Date().getTime() - d.getTime();
      System.out.println("AFTER " + difference); 
   } 
   
   public static void main(String[] args) { 
      System.out.println("Bye"); 
   } 
     
   private static int process(int valueIn) { 
      for(int i=0; i < 1000; i++) { 
         int wrkInt = valueIn & staticIntArray[i]; 
      } 
      return valueIn; 
    } 

   public static String getDateString() { 
      java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("mm:ss:SSS"); 
      TimeZone tz = TimeZone.getDefault(); 
      dateFormat.setTimeZone(tz); 
      return dateFormat.format(new Date()); 
   } 
} 
