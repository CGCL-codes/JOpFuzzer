
public class TestMe {
  public static void main(String args[]) {
  long numExceptions = 0L;
  String str = "";
  Object o = str;
  for (int i = 0; i < 10000000; i++) {
   try {
    double x = ((Number)o).doubleValue();
   } catch (ClassCastException ex) {
    numExceptions++;
   }
  }
  System.out.println("numExceptions="+numExceptions);
 }
}

