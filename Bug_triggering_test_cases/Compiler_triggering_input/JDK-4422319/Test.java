import java.util.prefs.*;
import java.util.*;
 
 
public class PutByteArray {
    static final int TOTAL = 10000;
    static final int ARRAYSIZE = 100;
    public static void main(String[] args) throws Exception {
        
        boolean status[];
        int passcount = 0, failcount = 0;
        int numTests = 2;
        int i, j = 0;
    
        status = new boolean[numTests];
        System.out.println("Total tests for Exec : "+numTests);
         
        for(i = 0; i < numTests; ++i) {
// PutByteArray all tests in try... catch block
       try {
++j;
switch (j) {
case 1:
status[i] = PutByteArrayTest01();
break;
case 2:
status[i] = PutByteArrayTest02();
break;
}

  } catch (Exception e) {
             status[i] = false;
System.out.println ("Exception in test "+j+": "+e.getMessage());
e.printStackTrace();
            }
        } // end for

        // Get pass and fail totals
        for(i = 0; i < numTests; ++i) {
       if (status[i] == true)
passcount++;
else
failcount++;
        }
    
        System.out.println("Pass count: " + passcount);
        System.out.println("Fail count: " + failcount);

        // check if tests passed
        if ( failcount < 1 ) {
           System.out.println("Test for PutByteArray.java Passed");
           System.exit(0);
        } else {
           System.out.println("Test for PutByteArray.java Failed");
           System.exit(1);
        }
        
     } // end main
  

 
/**
 *
 *PutByteArrayTest01: java.util.prefs.Preferences void putByteArray(java.lang.String key, byte[] value)
 *Create userRoot preferences
 *Create "N1" node in userRoot
 *In "N1" put key "k1" and value byte[] value
 *verify byte[] value is put by calling getByteArray() method, if ok then pass.
 *
 */
   public static boolean PutByteArrayTest01() {

boolean bReturn = false;
       try {
           Preferences userRoot = Preferences.userRoot();
           Preferences N1 = userRoot.node("N1");
           //N1.clear();
          
           byte [] value = new byte[(Preferences.MAX_VALUE_LENGTH*3/4)];
           for (int i =0; i < (Preferences.MAX_VALUE_LENGTH*3/4); i++) {
               value[i]=(byte)i;
           }
           N1.putByteArray("k1",value);
           byte[] valueExpected = value;
           byte[] valuedefault = new byte[1];
           valuedefault[0] = (byte)1;
           byte[] valueGot = N1.getByteArray("k1",valuedefault);
          
           for (int i=0; i<(Preferences.MAX_VALUE_LENGTH*3/4); i++) {
                //System.out.println("value = " + value[i]);
                //System.out.println("valueGot = " + valueGot[i]);
                if (valueGot[i] != (valueExpected[i])) {
                    throw new Exception("valueGot[i] != (valueExpected[i] not ok in PutByteArrayTest01()");
                }
           }
           bReturn = true;
           System.out.println("PutByteArrayTest01() Pass");
       } catch(Exception e) {
           bReturn = false;
           System.out.println("Exception thrown = " + e);
           System.out.println("PutByteArrayTest01() Fail");
           e.printStackTrace();
       }
       return bReturn;
    }
/**
 *
 *PutByteArrayTest02: java.util.prefs.Preferences void putByteArray(java.lang.String key, byte[] value)
 *Create userRoot preferences
 *Create "N1" node in userRoot
 *In "N1" put key "k1" and value byte[] value
 *length of byte array is greater than MAX_VALUE_LENGTH*3/4 then if IllegalArgumentException is thrown then pass.
 *
 */
   public static boolean PutByteArrayTest02() {

boolean bReturn = false;
       try {
           Preferences userRoot = Preferences.userRoot();
           Preferences N1 = userRoot.node("N1");
           //N1.clear();
          
           byte [] value = new byte[(Preferences.MAX_VALUE_LENGTH*3/4) +1];
           for (int i =0; i < (Preferences.MAX_VALUE_LENGTH*3/4 +1); i++) {
               value[i]=(byte)i;
           }
           N1.putByteArray("k1",value);
           bReturn = false;
           System.out.println("PutByteArrayTest02() Fail");
       } catch(IllegalArgumentException iae) {
           bReturn = true;
           System.out.println("Expected IllegalArgumentException thrown PutByteArrayTest02()Pass");
       } catch(Exception e) {
           bReturn = false;
           System.out.println("Exception thrown = " + e);
           System.out.println("PutByteArrayTest02() Fail");
           e.printStackTrace();
       }
       return bReturn;
    }
 }
