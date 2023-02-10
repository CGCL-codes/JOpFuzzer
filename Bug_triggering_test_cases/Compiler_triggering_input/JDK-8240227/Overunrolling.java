public class Overunrolling {

    public static int foo = 3;
    public static int bar = 55;
    
    // The inner for-loop is over-unrolled and vectorized resulting in
    // a crash in the matcher because the memory input to a vector is top.
    public static int test5(int[] array) {
        int result = 0;
        int[] iArr = new int[8];
        for (int i = 0; i < array.length; i++) {
            for (int j = 5; j < i; j++) {
                if (foo == 42) {
                    bar = 34;
                }
                iArr[j] += array[j];
                result += array[j];
            }
        }
        return result;
    }

    public static void main(String args[]) {
        int[] array = new int[8];
        test5(array);
    }
}
