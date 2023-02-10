
import java.util.List;
import java.util.ArrayList;
  class findFactors {


     public static void main(String...args) {

       int x = 20;
       List<Integer> arr = new ArrayList<Integer>();
       for (int i=0; i < 20/2; i++) {
          if ( x%i == 0 ) {
              arr.add(i);

           }
       }

       for(int factor : arr) {
         System.out.println(factor + " factor");
       }

    }
 }
