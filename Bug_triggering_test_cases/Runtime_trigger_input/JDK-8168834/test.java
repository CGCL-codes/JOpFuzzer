

import com.sun.jna.Library;
import com.sun.jna.Native;


public class Main {

   public interface NativeClassClient extends Library {
        public static final NativeClassClient INSTANCE = (NativeClassClient) Native.loadLibrary("example", NativeClassClient.class);
        public  void Java_com_company_NativeClassClient_GetRequestCode(String prgName, int options, String additionalString);
    }
    public static void main(String[] args) {
        try{
            NativeClassClient.INSTANCE.Java_com_company_NativeClassClient_GetRequestCode("Organizations", 12, "tstAdFile.cfg");
        }
        catch (UnsatisfiedLinkError e)
        {
            System.out.println("method no found (" + e + ")");
        }
       }
}
