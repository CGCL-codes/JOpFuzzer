
public class LogTest {

    public static void main(String[] args)
    {
        double n = Integer.parseInt("17197");
        System.out.println("n=" + n);
        double d = Math.log(n);
        for (int i = 0; i < 100000; i++) {
            double e = Math.log(n);
            if (e != d) {
                System.err.println("ERROR after " + i + " iterations:\n" +
                   "previous value: " + d + " (" +
                   Long.toHexString(Double.doubleToLongBits(d)) + ")\n" +
                   " current value: " + e + " (" +
                   Long.toHexString(Double.doubleToLongBits(e)) + ")");
                System.exit(1);
            }
        }
        System.err.println("SUCCESS!");
        System.exit(0);
    }
}

