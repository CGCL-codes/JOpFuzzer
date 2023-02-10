public class Reduced2 {

    static int test() {
        int array[] = new int[50];

        float f = 0;
        for (int i = 3; i < 49; i++) {
            for (long l = 1; l < i; l++) {
                array[(int)l] = i;
                f += l;
            }
        }
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        return sum;
    }

    public static void main(String[] args) {
        long expected = test();
        for (int i = 0; i < 10_000; i++) {
            int res = test();
            if (res != expected) {
                throw new RuntimeException("Unexpected result: " + res + " != " + expected);
            }
        }
    }
}
