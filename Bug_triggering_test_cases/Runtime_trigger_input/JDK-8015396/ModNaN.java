public class ModNaN {

/* Windows 7/Core i7 980X/1.6.0_38 (64):
NaNs
8.98846567431158E307 % 1.295163E-318 = NaN
1.7976931348623157E308 % 2.59032E-318 = NaN
1.7976931348623157E308 % 1.060997895E-314 = NaN
1.7976931348623157E308 % 6.767486E-317 = NaN
1.7976931348623157E308 % 7.528725E-318 = NaN
non-NaNs
8.98846567431158E307 % 1.29516E-318 = 2.53E-321
1.7976931348623157E308 % 2.590327E-318 = 0.0
1.7976931348623157E308 % 1.060965516E-314 = 9.35818525E-315
*/
/* Windows 7/Core i7 980X/1.7.0_11 (64):
NaNs
8.98846567431158E307 % 1.295163E-318 = 0.0
1.7976931348623157E308 % 2.59032E-318 = 2.57135E-318
1.7976931348623157E308 % 1.060997895E-314 = 5.31535078E-315
1.7976931348623157E308 % 6.767486E-317 = 1.142612E-317
1.7976931348623157E308 % 7.528725E-318 = 4.64634E-318
non-NaNs
8.98846567431158E307 % 1.29516E-318 = 2.53E-321
1.7976931348623157E308 % 2.590327E-318 = 0.0
1.7976931348623157E308 % 1.060965516E-314 = 9.35818525E-315
*/
    public static void main(String[] args) {
        System.out.println("NaNs");
        for (double[] ab : new double[][]{
                new double[]{Double.longBitsToDouble(0x7FE0000000000000L),Double.longBitsToDouble(0x0000000000040000L)},
                new double[]{Double.longBitsToDouble(0x7FEFFFFFFFFFFFFFL),Double.longBitsToDouble(0x000000000007FFFFL)},
                //
                new double[]{Double.longBitsToDouble(0x7FEFFFFFFFFFFFFFL),Double.longBitsToDouble(0x000000007FFFFFFFL)},
                new double[]{Double.longBitsToDouble(0x7FEFFFFFFFFFFFFFL),6.767486E-317},
                new double[]{Double.longBitsToDouble(0x7FEFFFFFFFFFFFFFL),7.528725E-318},
        }) {
            double a = ab[0];
            double b = ab[1];
            double mod = a % b;
            System.out.println(a+" % "+b+" = "+mod);
        }

        System.out.println("non-NaNs");
        for (double[] ab : new double[][]{
                new double[]{Double.longBitsToDouble(0x7FE0000000000000L),Double.longBitsToDouble(0x000000000003FFFFL)},
                new double[]{Double.longBitsToDouble(0x7FEFFFFFFFFFFFFFL),Double.longBitsToDouble(0x0000000000080000L)},
                //
                new double[]{Double.longBitsToDouble(0x7FEFFFFFFFFFFFFFL),Double.longBitsToDouble(0x000000007FFEFFFFL)},
        }) {
            double a = ab[0];
            double b = ab[1];
            double mod = a % b;
            System.out.println(a+" % "+b+" = "+mod);
        }
    }
}
