public class Reduced {
    public static long y;
    static int iArrFld[] = new int[400];
    static long x = 0;

    public static void main(String[] strArr) {
        for (int i1 = 0; i1 < 100; i1++)
            vMeth(3, 5);
    }
    static void vMeth(int f, int g) {
        int i3 = 23;
        int i11 = 2, i12 = 12, i13 = 32901, i14 = 43741;
        for (i11 = 7; i11 < 325; ++i11) {
            i13 = 1;
            while ((i13 += 3) < 5) {
                iArrFld[i13 - 1] = 906;
                for (i14 = i13; i14 < 5; i14 += 2) {
                    y += i14;
                    i3 += i14;
                }
            }
        }
        x += i11 + i12 + i13 + i14;
    }
}
