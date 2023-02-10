public class LayoutTest {

    Small.val small;;
    Big.val big;;


    public LayoutTest() {
        small = new Small();
        big = new Big();
    }

    static value class Small {
        final int i;
        final Big.val big;

        private Small() {
            i = 3;
            big = new Big();
        }
    }

    static value class Big {
        long l0,l1,l2,l3,l4,l5,l6,l7,l8,l9;
        long l10,l11,l12,l13,l14,l15,l16,l17,l18,l19;
        long l20,l21,l22,l23,l24,l25,l26,l27,l28,l29;

        private Big() {
            l0 = l1 = l2 = l3 = l4 = l5 = l6 = l7 = l8 = l9 = 5;
            l10 = l11 = l12 = l13 = l14 = l15 = l16 = l17 = l18 = l19 = 7;
            l20 = l21 = l22 = l23 = l24 = l25 = l26 = l27 = l28 = l29 = 11;
        }
    }

    public static void main(String[] args) {
        LayoutTest test = new LayoutTest();
        long l = test.work();
    }

    long work() {
        return small.i + small.big.l0 + big.l0;
    }
}