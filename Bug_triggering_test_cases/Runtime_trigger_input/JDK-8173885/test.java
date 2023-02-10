
package ssl;
public class ExtractElpro {

    static {
        System.load("C:\\path\\to\\dll\\ConsoleApplication2.dll");
    }

    public native String GetElproPDFXMLAndMoveFiles(String a);

    public static void main (String[] args) {
    	try{
    		ExtractElpro e = new ExtractElpro();
    		System.out.println(e.GetElproPDFXMLAndMoveFiles("C:/path/to/pdf/file.pdf"));
    	} catch (Exception e){
    		System.out.println(e.getStackTrace());
    	}
    }
}
