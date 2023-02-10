import java.math.BigDecimal;

/*
  cdf file (compile only BigDecimal constructor):
    [ { match: ["java/math/BigDecimal*.*init*"], c1: { Exclude:false }, c2: { Exclude:false } },
      { match: ["*.*"], c1: { Exclude:true }, c2: { Exclude:true } } ]
  $ ./jdk-14/fastdebug/bin/java -XX:CompilerDirectivesFile=cdf.txt TestFMA

  The issue reproduced when BigDecimal constructor compilation is enabled: the resulting object contains incorrect value.
  The issue happens a number of cycles after last compilation.
  The issue is not related with safepoints.
*/
public class TestFMA {

    static double test2(double a, double b, double c) {
        return fma(a, b, c);
    }

    static double test4(double a, double b, double c) {
        return fma(-a, b, c);
    }

    static public void main(String[] args) throws Exception {
        TestFMA t = new TestFMA();
        for (int i = 0; i < 20000; i++) {
            double a = 5;
            double res = fma(a, 10.0D, 7.0D);
        }

        for (int i = 0; i < 20000; i++) {
            System.out.print(".");
            double a = 5;
            double res = fma(-a, 10.0D, 7.0D);
            if (res != -43.0D) {
                System.out.println("FAIL!");
                //System.out.println((double)res + " != " + -43.0D);
                //System.out.println(ba + " " + bb + " " + bc);
                System.exit(1);
            }
        }
    }

    static BigDecimal ba;
    static BigDecimal bb;
    static BigDecimal bc;

    public static double fma(double a, double b, double c) {
                ba = new BigDecimal(a);
                bb = new BigDecimal(b);
                bc = new BigDecimal(c);
                BigDecimal product = ba.multiply(bb);
                return product.add(bc).doubleValue();
    }
}

