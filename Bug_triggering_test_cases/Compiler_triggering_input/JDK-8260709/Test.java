// Generated by Java* Fuzzer test generator (1.0.001). Fri Jan 29 00:09:05 2021
public class Test {

    public static final int N = 400;

    public static long instanceCount=9L;
    public int iFld=-17946;
    public static short sFld=-23017;
    public static float fFld=54.375F;
    public static double dFld=-1.23520;
    public static boolean bArrFld[]=new boolean[N];
    public int iArrFld[]=new int[N];
    public static long lArrFld[]=new long[N];

    static {
        FuzzerUtils.init(Test.bArrFld, true);
        FuzzerUtils.init(Test.lArrFld, -3573303018L);
    }

    public static long vSmallMeth_check_sum = 0;
    public static long vMeth_check_sum = 0;
    public static long iMeth_check_sum = 0;

    public static int iMeth(long l2, long l3) {

        int i3=-1, i4=-225, i5=4795;
        float f1=-32.524F;
        boolean b=false;
        long lArr[]=new long[N];

        FuzzerUtils.init(lArr, -4L);

        i3 += (int)l2;
        i4 = 1;
        while (++i4 < 328) {
            lArr[i4 - 1] = i4;
            i3 += (((i4 * Test.fFld) + i4) - i3);
            i3 = i3;
            Test.dFld = Test.fFld;
            Test.dFld += i3;
            for (f1 = 1; f1 < 5; ++f1) {
                i5 = i4;
                i3 = (int)f1;
                if (b) {
                    i5 = (int)3728212297L;
                    Test.fFld += (float)Test.dFld;
                } else if (b) {
                    l2 >>= -5;
                    i5 = i4;
                }
            }
        }
        long meth_res = l2 + l3 + i3 + i4 + Float.floatToIntBits(f1) + i5 + (b ? 1 : 0) + FuzzerUtils.checkSum(lArr);
        iMeth_check_sum += meth_res;
        return (int)meth_res;
    }

    public static void vMeth(long l, long l1) {

        int i=-35256, i1=31, i2=14195, i6=215, i7=-47826, i8=-192, i9=163, iArr[]=new int[N];
        boolean b1=false;

        FuzzerUtils.init(iArr, -10);

        i = i;
        i += (Short.reverseBytes(Test.sFld) * (i + (i++)));
        i += (int)((Test.sFld * (i - Test.fFld)) * ((Test.fFld--) + (Test.fFld + i)));
        Test.bArrFld = Test.bArrFld;
        for (i1 = 10; i1 < 182; ++i1) {
            i2 >>>= iMeth(l1, l1);
            for (i6 = 9; i6 > 1; --i6) {
                Test.dFld -= l1;
                for (i8 = 2; 1 < i8; --i8) {
                    l += i8;
                    i7 = i2;
                    i7 += (int)Test.fFld;
                    if (b1) continue;
                    iArr[i6 + 1] |= i9;
                    try {
                        i = (i7 / -38689);
                        i7 = (i9 % 838006886);
                        i2 = (i6 / 7321);
                    } catch (ArithmeticException a_e) {}
                }
            }
        }
        vMeth_check_sum += l + l1 + i + i1 + i2 + i6 + i7 + i8 + i9 + (b1 ? 1 : 0) + FuzzerUtils.checkSum(iArr);
    }

    public static void vSmallMeth(byte by) {


        vMeth(Test.instanceCount, 102040389746869171L);
        Test.instanceCount = (long)Test.dFld;
        vSmallMeth_check_sum += by;
    }

    public void mainTest(String[] strArr1) {

        float f=107.460F;
        byte by1=-42, byArr[]=new byte[N];
        int i10=-93, i12=51558, i13=-34923, i14=-46709, i15=11, iArr1[]=new int[N];
        boolean b2=false;

        FuzzerUtils.init(byArr, (byte)73);
        FuzzerUtils.init(iArr1, 29935);

        f *= iFld;
        for (int smallinvoc=0; smallinvoc<574; smallinvoc++) vSmallMeth((byte)(-81));
        by1 *= (byte)iFld;
        iArrFld[(iFld >>> 1) % N] *= by1;
        iFld = iFld;
        i10 = 1;
        while (++i10 < 396) {
            Test.instanceCount = -62763;
            iArrFld[i10] = iFld;
            for (i12 = 2; i12 < 64; i12++) {
                Test.fFld = i12;
                for (i14 = 1; i14 < 2; ++i14) {
                    switch ((i12 % 10) + 37) {
                    case 37:
                    case 38:
                        byArr[i10] = (byte)Test.dFld;
                        i13 = iFld;
                        switch (((i12 >>> 1) % 2) + 48) {
                        case 48:
                            Test.sFld += (short)(-5 + (i14 * i14));
                            iArr1[i14 + 1] -= i10;
                            iArrFld[i12] |= (int)Test.instanceCount;
                            iFld *= -81;
                            break;
                        case 49:
                            i13 += i14;
                            Test.fFld *= i12;
                            f *= (float)Test.dFld;
                            b2 = b2;
                        }
                        break;
                    case 39:
                    case 40:
                        Test.sFld <<= (short)-9162L;
                        i15 = (int)-8108184587028220462L;
                        iFld += (((i14 * by1) + f) - i14);
                        break;
                    case 41:
                        iFld += i14;
                        if (b2) {
                            iArr1[i10 - 1] -= i12;
                            Test.lArrFld[i12 + 1] += i15;
                        } else if (b2) {
                            i15 >>= 6;
                        }
                        break;
                    case 42:
                        f += i10;
                        break;
                    case 43:
                        iFld |= i12;
                        break;
                    case 44:
                        by1 += (byte)(((i14 * i14) + Test.instanceCount) - i14);
                    case 45:
                        Test.sFld += (short)6;
                        break;
                    case 46:
                        i13 *= (int)Test.dFld;
                        break;
                    default:
                        iArrFld[i10] = 5;
                    }
                }
            }
        }

        FuzzerUtils.out.println("f by1 i10 = " + Float.floatToIntBits(f) + "," + by1 + "," + i10);
        FuzzerUtils.out.println("i12 i13 i14 = " + i12 + "," + i13 + "," + i14);
        FuzzerUtils.out.println("i15 b2 byArr = " + i15 + "," + (b2 ? 1 : 0) + "," + FuzzerUtils.checkSum(byArr));
        FuzzerUtils.out.println("iArr1 = " + FuzzerUtils.checkSum(iArr1));

        FuzzerUtils.out.println("Test.instanceCount iFld Test.sFld = " + Test.instanceCount + "," + iFld + "," +
            Test.sFld);
        FuzzerUtils.out.println("Test.fFld Test.dFld Test.bArrFld = " + Float.floatToIntBits(Test.fFld) + "," +
            Double.doubleToLongBits(Test.dFld) + "," + FuzzerUtils.checkSum(Test.bArrFld));
        FuzzerUtils.out.println("iArrFld Test.lArrFld = " + FuzzerUtils.checkSum(iArrFld) + "," +
            FuzzerUtils.checkSum(Test.lArrFld));

        FuzzerUtils.out.println("iMeth_check_sum: " + iMeth_check_sum);
        FuzzerUtils.out.println("vMeth_check_sum: " + vMeth_check_sum);
        FuzzerUtils.out.println("vSmallMeth_check_sum: " + vSmallMeth_check_sum);
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
//DEBUG  vSmallMeth ->  vSmallMeth mainTest Test
//DEBUG  vMeth ->  vMeth vSmallMeth mainTest Test
//DEBUG  iMeth ->  iMeth vMeth vSmallMeth mainTest Test
//DEBUG  Depth = 3
//DEBUG  Classes = 1
//DEBUG  static objects = {}
