
public class Test {

 public static void main ( String[] args ) {
    double dd = 95.2;
    System.out.println(dd);
		
    System.out.println("");
    float f = 95.2f;
    double d = f;
		
    System.out.println(f);
    System.out.println(d);
    System.out.println((float)d);
    
    System.out.println("");
    d = (new Float(f)).doubleValue();
    System.out.println(d);
    System.out.println((float)d);
  }
}
