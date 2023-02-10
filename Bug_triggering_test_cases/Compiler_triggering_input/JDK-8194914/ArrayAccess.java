public class ArrayAccess {

  public static void accessArrayVariables(int[] array, int i) {
    for (int j = 0; j < 100000; j++) {
      array[i-2]++;
      array[i-1]++;
    }
  }

  public void test() {
    int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    for (int i = 0; i < 2000; i++) {
	accessArrayVariables(array, 5);
    }
  }

  public static void main(String [] args) {
      ArrayAccess aa = new ArrayAccess();
      aa.test();
  }
}
