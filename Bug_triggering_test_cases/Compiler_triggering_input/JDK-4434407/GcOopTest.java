 /* Run this application in this form
$ java_g -Xcomp -XX:CompileOnly=.Test -XX:-Inline -XX:NewSize=1048576 -XX:+PrintScavenge -XX:+PrintCompilation
GcOopTest
 */
public class GcOopTest {
    public static final int ARRAY_SIZE = 1000000;
    public static void doGC(byte[] byteArray, int size) {
           int [] forceSvavengeArray = new int[size/2];
           for(int i = 0; i < size; i++) {
                 byteArray[i] = 10;
            }
            //System.gc();
    }
    public static boolean Test(int size) {
          size = size/2;
          byte [] byteArray = new byte [size];
           for(int i = 0; i < size; i++) {
                byteArray[i] = 1;
           }
 
           doGC(byteArray, size);
 
           for(int i = 0; i < size; i++) {
                 if(byteArray[i] != 10)
                 return false;
           }
           return true;
   }
 
   public static void main(String[] args) {
       int size = ARRAY_SIZE;
       char [] a = new char [1];
       byte [] b = new byte [1];
       a[0] = 1;
       b[0] = 1;
       boolean test = false;
       if((test = Test(size)) == true)
           System.out.println("Ok");
       else
       System.out.println("Error");
    }
 }
 