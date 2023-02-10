public class TestLoop{

    public static int test_loop(int x) {
        int sum = 0;
        if (x != 0) {
            for (int y = 1; y < Integer.MAX_VALUE; ++y) {
                if (y % x == 0) ++sum;
            }
        }
        return sum;
    }

    public static void main(String args[]) {
        int sum = test_loop(3);
        System.out.println("sum: " + sum);
    }
}