public class Test {
     public static void main(String[] arg) {
         int sum = 0;
         for (int i = 0; i < 1000; i++) {
             sum += test_aot(i);
         }
	 System.out.println(" sum = " + sum);
     }

     static int test_aot(int i) {
         int sum = i;
         for (int j = 0; j < 100; j++) {
             sum += test_c2(i * 7) / 3;
         }
         return sum;
     }

     static int test_c2(int i) {
         return i % 5;
     }
}
