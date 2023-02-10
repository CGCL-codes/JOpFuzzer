public class Test {

    public static final int N = 400;

    public static int iMeth() {
        int i3 = 240, i4=-14840, i5=13485;
        for (i4 = 303; i4 > 15; i4 -= 2) {
            int f=1;
            do {
                try {
                    i3 = (38726 / i5);
                    i3 = (i4 % -21500);
                    i5 = (i3 % 787);
                } catch (ArithmeticException a_e) {
                    System.out.println("f=" + f + ", ERR i3 = " + i3 + ", i5 = " + i5);
                    return 0;
                }
                i3 <<= i4;
                i5 <<= i5;
                i3 += (8 + (f * f));
                i5 >>= i5;
            } while (++f < 11);
        }
        return 0;
    }
    public static void main(String[] strArr) {
        iMeth();
    }
}