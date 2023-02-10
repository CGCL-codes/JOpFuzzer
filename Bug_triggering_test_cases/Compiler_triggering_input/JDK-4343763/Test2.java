public class Test2 {

    static boolean foo(double i) {
      return isNaN(i) || !isNaN(Double.NaN);
    }
  
    public static void main (String[] args) {
      // boolean b = isNaN(Double.NaN);
      // System.out.println (" b = " + b);
      System.out.println("isNaN(Double.Nan) = " + isNaN(Double.NaN));
      System.out.println("isNaN(Double.Nan) = " + isNaN(Double.NaN));
    }
    
    private static boolean isNaN (double v) {
      return (v != v);
    }
  }
  