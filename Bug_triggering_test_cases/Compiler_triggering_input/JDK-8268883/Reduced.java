public class Reduced {
    static final int N = 400;
    static long instanceCount;
    static float fFld;
    static int iFld1;
    static short sFld;
    static long vSmallMeth_check_sum;
    int iFld;

    public static void main(String[] strArr) {
      Reduced _instance = new Reduced();
        for (int i = 0; i < 10; i++) {
            _instance.mainTest();
        }
    }

    void mainTest() {
        short s = 838;
        int i31 = 238, i32, i33 = -1, i34 = 181, i35 = 155, i36 = 8401, i37 = 50, i38 = 153, iArr[][] = new int[N][N];
        float f1 = 46.763F, fArr[] = new float[N];
        byte byArr[] = new byte[N];
        boolean bArr[] = new boolean[N];
        init(byArr, (byte) 63);
        for (float f : fArr) {
            for (int smallinvoc = 0; smallinvoc < 62; smallinvoc++) {
                vSmallMeth(instanceCount = ((instanceCount++) * 65430), ((iFld + iFld) * (iFld++)), s);
            }
            vMeth(iFld);
            i31 = 1;
            do {
                sFld -= iFld1;
            } while (++i31 < 63);
        }
        for (i32 = 7; i32 < 160; i32++) {
            for (i34 = 1; i34 < 164; i34 += 3) {
                try {
                    iFld = (105 / i33);
                    iFld1 = (iArr[i34][1] % iArr[i32][i34 - 1] / i32);
                } catch (ArithmeticException a_e) {
                }
            }
            i33 <<= i31;
        }
        System.out.println(s + "," + i31 + "," + i32);
        System.out.println(i33 + "," + i34 + "," + i35);
        System.out.println(i36 + "," + i37 + "," + Float.floatToIntBits(f1));
        System.out.println(i38 + "," + Double.doubleToLongBits(FuzzerUtils.checkSum(fArr)) + "," + FuzzerUtils.checkSum(byArr));
        System.out.println("iArr bArr = " + FuzzerUtils.checkSum(iArr) + "," + FuzzerUtils.checkSum(bArr));
        System.out.println(instanceCount + "," + iFld + "," + Float.floatToIntBits(fFld));
    }

    static void init(byte[] a, byte seed) {
        for (int j = 0; j < a.length; j++) {
            a[j] = (byte) ((j % 2 == 0) ? seed + j : seed - j);
        }
    }

    static void vSmallMeth(long l, long l1, int i) {
        vSmallMeth_check_sum += l + l1 + i;
    }

    void vMeth(int i2) {
    }
}
