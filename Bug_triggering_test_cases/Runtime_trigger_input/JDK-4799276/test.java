
class cosbench {
    public static void main(String[] args) {
        final int MAX = 2000000;

        int max = (args.length < 1) ? MAX : Integer.parseInt(args[0]);
        System.out.println("Executing loop " + max + " times.");

        double s = 0;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < max; ++i) {
            s += Math.cos(i);
        }
        System.out.println("sum = " + s);

        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + " ms.");
    }
}
