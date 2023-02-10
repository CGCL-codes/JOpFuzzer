// Generated by Java* Fuzzer test generator (1.0.001). Wed Dec 30 07:59:19 2020
public class Test {

    public static final int N = 400;

    public static long instanceCount=-8669532722148771131L;
    public static long lFld=7275121050849254145L;
    public static double dFld=-1.118171;
    public static boolean bFld=false;
    public static float fArrFld[]=new float[N];
    public int iArrFld[]=new int[N];

    static {
        FuzzerUtils.init(Test.fArrFld, -2.173F);
    }

    public static long fMeth_check_sum = 0;
    public static long vMeth_check_sum = 0;
    public static long iMeth_check_sum = 0;

    public static int iMeth(byte by) {

        int i7=32792, i8=2936, i9=-3, i10=8, i11=84, i12=-3, i13=-60999, i14=-53596, iArr1[]=new int[N];
        double d1=-1.17109;
        float f1=-57.184F;

        FuzzerUtils.init(iArr1, -51958);

        for (i7 = 4; 284 > i7; i7++) {
            iArr1 = iArr1;
            if (false) continue;
        }
        i8 -= (int)d1;
        for (i9 = 10; 302 > i9; i9++) {
            i8 -= -194;
            i8 *= (int)Test.lFld;
        }
        for (i11 = 4; i11 < 138; i11 += 3) {
            iArr1[i11 - 1] += (int)f1;
            i12 = i9;
            i12 += (i11 * i12);
            for (i13 = 1; 35 > i13; i13++) {
                i8 += i13;
                f1 -= -7293016607437799567L;
                Test.fArrFld[i11] = i9;
            }
        }
        long meth_res = by + i7 + i8 + Double.doubleToLongBits(d1) + i9 + i10 + i11 + i12 + Float.floatToIntBits(f1) +
            i13 + i14 + FuzzerUtils.checkSum(iArr1);
        iMeth_check_sum += meth_res;
        return (int)meth_res;
    }

    public static void vMeth() {

        float f=-96.809F, fArr[]=new float[N];
        int i4=-28810, i5=2, i6=16409, i15=-25, i16=-110, iArr2[][]=new int[N][N];
        byte by1=79;
        short s1=-17354;
        double d2=-1.101735;
        boolean b=true;

        FuzzerUtils.init(fArr, 0.550F);
        FuzzerUtils.init(iArr2, 196);

        for (f = 8; f < 297; f++) {
            switch ((int)((f % 2) + 113)) {
            case 113:
                for (i5 = (int)(f); i5 < 6; ++i5) {
                    fArr[i5 - 1] *= ((long)((++i4) - (fArr[(int)(f)]--)) >>> (iMeth(by1) - i6));
                    s1 += (short)i6;
                    Test.lFld -= s1;
                    i4 *= (int)Test.instanceCount;
                    i4 = i6;
                    d2 = Test.instanceCount;
                    i6 += (int)f;
                    Test.instanceCount = i5;
                    iArr2[i5 - 1] = iArr2[i5];
                }
                for (i15 = 1; 6 > i15; ++i15) {
                    s1 *= (short)3L;
                    i16 = i4;
                }
                break;
            case 114:
                if (b) break;
                break;
            default:
                Test.instanceCount += (long)f;
            }
        }
        vMeth_check_sum += Float.floatToIntBits(f) + i4 + i5 + i6 + by1 + s1 + Double.doubleToLongBits(d2) + i15 + i16
            + (b ? 1 : 0) + Double.doubleToLongBits(FuzzerUtils.checkSum(fArr)) + FuzzerUtils.checkSum(iArr2);
    }

    public static float fMeth(double d) {

        int i2=-14, i3=-80, i17=3, i18=-5, iArr[]=new int[N];
        short s=-15838;
        double dArr[]=new double[N];
        byte byArr[]=new byte[N];

        FuzzerUtils.init(iArr, -62231);
        FuzzerUtils.init(dArr, -28.57706);
        FuzzerUtils.init(byArr, (byte)-126);

        for (int i1 : iArr) {
            i1 <<= (int)(Test.lFld + i1);
            for (i2 = 1; i2 < 4; i2++) {
                s = (short)0;
                dArr[i2] = Math.min((-(29750 - Test.instanceCount)) + (-1 + iArr[i2 + 1]), Test.lFld);
                vMeth();
                byArr[(-143 >>> 1) % N] *= (byte)i3;
                Test.lFld -= 10;
                i3 = 201;
                Test.instanceCount -= 63326;
                if (false) break;
            }
            for (i17 = 1; i17 < 4; ++i17) {
                Test.lFld *= i1;
                Test.lFld *= Test.instanceCount;
                Test.lFld += (i17 * i17);
            }
        }
        long meth_res = Double.doubleToLongBits(d) + i2 + i3 + s + i17 + i18 + FuzzerUtils.checkSum(iArr) +
            Double.doubleToLongBits(FuzzerUtils.checkSum(dArr)) + FuzzerUtils.checkSum(byArr);
        fMeth_check_sum += meth_res;
        return (float)meth_res;
    }

    public void mainTest(String[] strArr1) {

        int i=-11, i19=0, i20=-59997, i21=24505, i22=6, i23=-6, i24=-134, i25=-14;
        byte by2=122;
        long lArr[]=new long[N];
        boolean bArr[]=new boolean[N];

        FuzzerUtils.init(lArr, 713342504L);
        FuzzerUtils.init(bArr, false);

        i = Math.max((int)(fMeth(Test.dFld) + 15002), i);
        for (i19 = 2; i19 < 285; ++i19) {
            for (i21 = 2; i21 < 89; i21++) {
                Test.dFld = i22;
                lArr = lArr;
                if (Test.bFld) break;
                i23 = 1;
                while (++i23 < 2) {
                    i20 /= (int)(i22 | 1);
                    i22 += (int)Test.instanceCount;
                    i22 += (int)46238L;
                    i22 = 3;
                }
                bArr[i21 - 1] = Test.bFld;
                for (i24 = 1; i24 < 2; ++i24) {
                    float f2=1.306F;
                    Test.lFld &= i21;
                    f2 = i19;
                    f2 = i23;
                    switch ((i24 % 7) + 90) {
                    case 90:
                        iArrFld = iArrFld;
                        Test.lFld = Test.lFld;
                        i20 += (-168 + (i24 * i24));
                        i22 += -1;
                    case 91:
                        lArr[i21] = -38734;
                        i22 ^= (int)Test.lFld;
                        break;
                    case 92:
                        i += (i24 * i24);
                        break;
                    case 93:
                        lArr[i24 - 1] -= i25;
                        iArrFld[i21] *= (int)f2;
                        break;
                    case 94:
                        if (Test.bFld) continue;
                        by2 += (byte)(-136 + (i24 * i24));
                        Test.dFld *= i25;
                        i -= i24;
                        break;
                    case 95:
                        by2 += (byte)i24;
                        break;
                    case 96:
                        i20 >>= i;
                        break;
                    default:
                        iArrFld[i24 - 1] -= (int)f2;
                    }
                }
            }
        }

        FuzzerUtils.out.println("i i19 i20 = " + i + "," + i19 + "," + i20);
        FuzzerUtils.out.println("i21 i22 i23 = " + i21 + "," + i22 + "," + i23);
        FuzzerUtils.out.println("i24 i25 by2 = " + i24 + "," + i25 + "," + by2);
        FuzzerUtils.out.println("lArr bArr = " + FuzzerUtils.checkSum(lArr) + "," + FuzzerUtils.checkSum(bArr));

        FuzzerUtils.out.println("Test.instanceCount Test.lFld Test.dFld = " + Test.instanceCount + "," + Test.lFld +
            "," + Double.doubleToLongBits(Test.dFld));
        FuzzerUtils.out.println("Test.bFld Test.fArrFld iArrFld = " + (Test.bFld ? 1 : 0) + "," +
            Double.doubleToLongBits(FuzzerUtils.checkSum(Test.fArrFld)) + "," + FuzzerUtils.checkSum(iArrFld));

        FuzzerUtils.out.println("iMeth_check_sum: " + iMeth_check_sum);
        FuzzerUtils.out.println("vMeth_check_sum: " + vMeth_check_sum);
        FuzzerUtils.out.println("fMeth_check_sum: " + fMeth_check_sum);
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
//DEBUG  fMeth ->  fMeth mainTest
//DEBUG  vMeth ->  vMeth fMeth mainTest
//DEBUG  iMeth ->  iMeth vMeth fMeth mainTest
//DEBUG  Depth = 3
//DEBUG  Classes = 1
//DEBUG  static objects = {}
