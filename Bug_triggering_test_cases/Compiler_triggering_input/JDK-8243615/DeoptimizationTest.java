public class UnstableIfTest
{
    private UnstableIfTest() {}
    static int compute(double x)
    {
        return (int) (Math.sin(x) + Math.cos(x));
    }
    static void hotMethod(int iteration)
    {
        if (iteration < 20) {
            compute(78.3);
        }
        else if (iteration < 40) {
            compute(78.3);
        }
        else if (iteration < 60) {
            compute(78.3);
        }
        else if (iteration < 80) {
            compute(78.3);
        } else if (iteration < 100) {
            compute(78.3);
        else {
            compute(78.3);
        }
    }
    static void hotMethodWrapper(int iteration)
    {
        int count = 100_000;
        for (int i = 0; i < count; i++) {
            hotMethod(iteration);
        }
    }
    public static void main(String[] args)
    {
        for (int k = 0; k < 100; k++) {
            long start = System.nanoTime();
            hotMethodWrapper(k + 1);
            System.out.println("iteration " + k + ": " + (System.nanoTime() - start) / 1_000_000 + "ms");
        }
    }
}
