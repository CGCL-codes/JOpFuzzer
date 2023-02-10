public class MathTest {

    public static void main(String[] args) {
        MathTest test = new MathTest();
        while (true) {
            // y must be negative, x can be anything
            long x = -Math.round(Math.random() * 65535L);
            long y = x;
            test.compute(x, y);
        }
    }

    long a = 1;
    long b = -1;

    public void compute(long x, long y) {
        // if the next line is commented out, the problem does not occur
        long answer1 = a * x - b * y;
        long answer2 = a * y + b * x;
        System.out.println("should be 0: " + answer2);
    }
}