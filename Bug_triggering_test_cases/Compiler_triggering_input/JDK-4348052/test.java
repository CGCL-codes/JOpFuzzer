import java.util.*;

class test {
   public Vector factor(long num) {
      Vector factors = new Vector();
      double l=0;
      for (double t = 2; t < num; t++) {
         l = (num / t);
         if ( l == Math.round(num / t)) {
            factors.addElement(new Long((long)t));
            System.out.println(t);
         }
      }

      return factors;
   }
   public static void main(String[] args) {
      test t = new test();
      int m_WorkData = 4000013;
      long start = System.currentTimeMillis();
      t.factor(m_WorkData);
      long end = System.currentTimeMillis();
      long computeTime = end - start;
      //milliseconds
      System.out.println(m_WorkData+ ": Computing time = "+computeTime);

   }
}
