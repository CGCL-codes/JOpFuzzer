// Generated by Java* Fuzzer test generator (1.0.001). Sun Jan 23 23:53:54 2022
public class Test {

    public static final int N = 400;

    public static long instanceCount=-8L;
    public static short sFld=-15977;
    public static int iFld=-233;
    public static double dFld=86.25145;
    public static boolean bFld=false;
    public static int iArrFld[]=new int[N];
    public static float fArrFld[]=new float[N];

    static {
        FuzzerUtils.init(Test.iArrFld, 4);
        FuzzerUtils.init(Test.fArrFld, 82.726F);
    }

    public static long vMeth_check_sum = 0;
    public static long vMeth1_check_sum = 0;
    public static long dMeth_check_sum = 0;

    public static double dMeth(int i11, int i12, int i13) {

        int i14=4546;
        boolean b=false;

        i14 = 1;
        while (++i14 < 276) {
            if (b) break;
            i12 = 110;
        }
        long meth_res = i11 + i12 + i13 + i14 + (b ? 1 : 0);
        dMeth_check_sum += meth_res;
        return (double)meth_res;
    }

    public static void vMeth1(int i5, long l2, int i6) {

        int i7=-6, i8=89, i9=-117, i10=65417, i15=49, i16=2;
        double d=1.26929;

        i5 *= i5;
        for (i7 = 304; i7 > 7; --i7) {
            for (i9 = i7; i9 < 6; i9++) {
                i8 = (int)(dMeth(i6, i9, i6) * -5);
            }
            i8 *= i8;
            if (true) break;
            d = 1;
            while (++d < 6) {
                for (i15 = 1; i15 < 1; ++i15) {
                    Test.instanceCount = i16;
                    if (i5 != 0) {
                        vMeth1_check_sum += i5 + l2 + i6 + i7 + i8 + i9 + i10 + Double.doubleToLongBits(d) + i15 + i16;
                        return;
                    }
                    try {
                        i10 = (Test.iArrFld[i15 + 1] / -18651);
                        Test.iArrFld[(int)(d)] = (3723 / i10);
                        i16 = (i15 % i9);
                    } catch (ArithmeticException a_e) {}
                    Test.sFld *= (short)-29;
                }
                if (Test.iFld != 0) {
                    vMeth1_check_sum += i5 + l2 + i6 + i7 + i8 + i9 + i10 + Double.doubleToLongBits(d) + i15 + i16;
                    return;
                }
                i6 -= i8;
                i6 = (int)l2;
            }
        }
        vMeth1_check_sum += i5 + l2 + i6 + i7 + i8 + i9 + i10 + Double.doubleToLongBits(d) + i15 + i16;
    }

    public static void vMeth(long l, int i, int i1) {

        int i2=83, i3=3641, i4=-180, i17=7, i18=10290;
        long l1=-7456762619412748479L;
        float f=0.936F;
        byte by=123;
        boolean bArr[]=new boolean[N];
        double dArr[]=new double[N];

        FuzzerUtils.init(bArr, true);
        FuzzerUtils.init(dArr, -2.130134);

        for (i2 = 5; i2 < 256; i2++) {
            for (l1 = i2; l1 < 6; l1++) {
                Test.instanceCount ^= (i3 * (++l));
                vMeth1(7, l1, i1);
                i1 += (int)f;
                bArr[(int)(l1 + 1)] = false;
            }
        }
        Test.fArrFld[(i1 >>> 1) % N] = (float)Test.dFld;
        Test.iFld = Test.sFld;
        Test.dFld += f;
        if (Test.bFld) {
            i17 = 1;
            do {
                i18 = 1;
                do {
                    dArr = dArr;
                    dArr[i18] *= i18;
                } while (++i18 < 5);
                by *= (byte)71.73002;
            } while (++i17 < 303);
            vMeth_check_sum += l + i + i1 + i2 + i3 + l1 + i4 + Float.floatToIntBits(f) + i17 + i18 + by +
                FuzzerUtils.checkSum(bArr) + Double.doubleToLongBits(FuzzerUtils.checkSum(dArr));
            return;
        } else {
            bArr[(i4 >>> 1) % N] = Test.bFld;
        }
        vMeth_check_sum += l + i + i1 + i2 + i3 + l1 + i4 + Float.floatToIntBits(f) + i17 + i18 + by +
            FuzzerUtils.checkSum(bArr) + Double.doubleToLongBits(FuzzerUtils.checkSum(dArr));
    }

    public void mainTest(String[] strArr1) {

        int i19=-187, i20=-44938, i21=-16349, i22=192, i23=12, i24=-44378, i25=0, i26=226, i27=63675, i28=-8, i29=-59,
            i30=13;
        float f1=-2.96F;
        double d1=0.91439;
        byte by1=31;
        long lArr[][]=new long[N][N];

        FuzzerUtils.init(lArr, -2927930785L);

        vMeth(Test.instanceCount, Test.iFld, 8);
        try {
            for (i19 = 6 - 400; i19 < 138; i19 += 2) {
                if (false) {
                    i20 -= (int)f1;
                    for (i21 = 3; i21 < 63; i21++) {
                        lArr[i19 - 1][i19] *= i22;
                        Test.iFld = i19;
                        i23 = 1;
                        while (++i23 < 2) {
                            i20 = i23;
                            Test.sFld += (short)i20;
                            Test.iArrFld[i21] = i22;
                            Test.iArrFld[i23] -= i23;
                        }
                    }
                    for (d1 = 1; (63 + 400) > d1; ++d1) {
                        i20 += (int)(d1 * Test.instanceCount);
                        i20 -= (int)Test.instanceCount;
                    }
                }
            }
            for (i25 = 12; i25 < (245 + 400); i25++) {
                i24 += (((i25 * by1) + f1) - Test.instanceCount);
                by1 <<= (byte)i23;
                for (i27 = 63 + 400; i27 > 3; i27 -= 2) {
                    i20 += (0 + (i27 * i27));
                    Test.sFld *= (short)i20;
                    Test.iArrFld[i27 - 1] = (int)Test.instanceCount;
                    Test.instanceCount *= Test.instanceCount;
                    Test.iArrFld[i27 - 1] >>>= i21;
                    i24 = i24;
                    for (i29 = 1 - 400; 1 > i29; i29++) {
                        Test.iArrFld[i29] <<= 31374;
                        i24 -= i22;
                        f1 *= i29;
                    }
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException exc1) {
            Test.sFld <<= (short)i21;
        }

        FuzzerUtils.out.println("i19 i20 f1 = " + i19 + "," + i20 + "," + Float.floatToIntBits(f1));
        FuzzerUtils.out.println("i21 i22 i23 = " + i21 + "," + i22 + "," + i23);
        FuzzerUtils.out.println("d1 i24 i25 = " + Double.doubleToLongBits(d1) + "," + i24 + "," + i25);
        FuzzerUtils.out.println("i26 by1 i27 = " + i26 + "," + by1 + "," + i27);
        FuzzerUtils.out.println("i28 i29 i30 = " + i28 + "," + i29 + "," + i30);
        FuzzerUtils.out.println("lArr = " + FuzzerUtils.checkSum(lArr));

        FuzzerUtils.out.println("Test.instanceCount Test.sFld Test.iFld = " + Test.instanceCount + "," + Test.sFld +
            "," + Test.iFld);
        FuzzerUtils.out.println("Test.dFld Test.bFld Test.iArrFld = " + Double.doubleToLongBits(Test.dFld) + "," +
            (Test.bFld ? 1 : 0) + "," + FuzzerUtils.checkSum(Test.iArrFld));
        FuzzerUtils.out.println("Test.fArrFld = " + Double.doubleToLongBits(FuzzerUtils.checkSum(Test.fArrFld)));

        FuzzerUtils.out.println("dMeth_check_sum: " + dMeth_check_sum);
        FuzzerUtils.out.println("vMeth1_check_sum: " + vMeth1_check_sum);
        FuzzerUtils.out.println("vMeth_check_sum: " + vMeth_check_sum);
    }
    public static void main(String[] strArr) {

        try {
            Test _instance = new Test();
            for (int i = 0; i < 10; i++ ) {
                _instance.mainTest(strArr);
            }
         } catch (Exception ex) {
            FuzzerUtils.out.println(ex.getClass().getCanonicalName());
         }
    }
}
///////////////////////////////////////////////////////////////////////
//DEBUG  Test ->  Test
//DEBUG  main ->  main
//DEBUG  mainTest ->  mainTest
//DEBUG  vMeth ->  vMeth mainTest
//DEBUG  vMeth1 ->  vMeth1 vMeth mainTest
//DEBUG  dMeth ->  dMeth vMeth1 vMeth mainTest
//DEBUG  Depth = 3
//DEBUG  Classes = 1
//DEBUG  static objects = {}