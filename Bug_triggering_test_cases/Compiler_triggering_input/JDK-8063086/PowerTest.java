import java.util.function.BiFunction;

public class PowerTest {

    private static final int N = 1000000;
    private static final double base = 5350.456329377186;
    private static final double exp = 2.0;

    private static double eval(final BiFunction<Double, Double, Double> f) {
        return f.apply(base, exp);
    }

    private void loop(final String s, final BiFunction<Double, Double, Double> f) {
        double x = eval(f);
        for (int i = 0; i < N; i++) {
            final double p = eval(f);
            if (x != p) {
                System.out.println(s + "\ni=" + i + "\nx=" + x + "\np=" + p);
            }
            x = p;
        }
    }

    public void mathPow() {
        loop("Math.pow()", Math::pow);
    }

    public void strictMathPow() {
        loop("StrictMath.pow()", StrictMath::pow);
    }

    public static void main(final String[] args) {
        final PowerTest pt = new PowerTest();
        pt.mathPow();
        pt.strictMathPow();
    }
}