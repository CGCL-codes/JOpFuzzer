// Generated by Java* Fuzzer test generator (1.0.001). Mon Apr 30 17:40:25 2018
public class Test {

    public static final int N = 400;

    public static long instanceCount=43186L;
    public static volatile float fFld=0.488F;
    public static volatile int iFld=143;
    public static volatile int iFld1=38;
    public boolean bFld=true;
    public static double dFld=-76.62249;
    public static volatile int iArrFld[]=new int[N];
    public static int iArrFld1[]=new int[N];

    static {
        FuzzerUtils.init(Test.iArrFld, 0);
        FuzzerUtils.init(Test.iArrFld1, -25498);
    }

    public static long lMeth_check_sum = 0;
    public static long vMeth_check_sum = 0;
    public static long vMeth1_check_sum = 0;

    public static void vMeth1(long l) {

        int i8=-19291, i9=-24011, i10=-27225, i11=-171, i12=29074, i13=-128, i14=99;
        double d1=-2.65425;
        short s=-9379;

        for (i8 = 14; 242 > i8; i8 += 3) {
            for (i10 = 1; i10 < 20; ++i10) {
                i9 >>>= -7226;
                d1 = Test.fFld;
                i9 = (int)5.539F;
                i12 = 1;
                do {
                    i9 += i12;
                    i11 += (i12 ^ i8);
                    i9 -= s;
                    i11 -= i9;
                    i11 += i12;
                } while (++i12 < 2);
                for (i13 = i8; i13 < 2; i13++) {
                    l = 11;
                    i14 -= (int)Test.fFld;
                    i11 = -55556;
                }
            }
        }
        vMeth1_check_sum += l + i8 + i9 + i10 + i11 + Double.doubleToLongBits(d1) + i12 + s + i13 + i14;
    }

    public static void vMeth(int i5) {

        int i6=-6, i7=-152, i15=-51694, i16=-128, i17=6, i18=12, i19=-12, iArr1[]=new int[N];
        double d2=73.21895, dArr[][]=new double[N][N];
        float f1=-69.468F;
        byte by=-68;

        FuzzerUtils.init(iArr1, -248);
        FuzzerUtils.init(dArr, 11.99670);

        i5 += (int)(++Test.instanceCount);
        for (i6 = 5; i6 < 161; i6 += 2) {
            iArr1[i6 - 1] -= (int)((Test.fFld--) * i7);
            dArr[i6 + 1][i6] = i5;
            vMeth1(53L);
            try {
                i5 = (i5 % 118545504);
                i7 = (i5 / 28846);
                i5 = (i6 % i5);
            } catch (ArithmeticException a_e) {}
            Test.instanceCount = (long)d2;
        }
        for (i15 = 12; 301 > i15; i15++) {
            for (f1 = 6; f1 > i15; f1 -= 3) {
                switch ((((-12910 >>> 1) % 10) * 5) + 127) {
                case 159:
                    Test.instanceCount <<= i7;
                    for (i18 = 1; 1 > i18; i18++) {
                        Test.instanceCount <<= Test.instanceCount;
                        i16 += i18;
                        i7 += i18;
                    }
                    break;
                case 163:
                    by ^= (byte)-2L;
                case 175:
                    i17 = 45468;
                    break;
                case 173:
                    Test.fFld -= f1;
                    break;
                case 130:
                    i17 -= (int)d2;
                    break;
                case 167:
                case 149:
                    Test.iFld += (int)f1;
                    break;
                case 133:
                    Test.fFld += f1;
                    break;
                case 161:
                    i7 = i19;
                case 137:
                    iArr1[(int)(f1 - 1)] = i7;
                    break;
                default:
                    i19 |= Test.iFld1;
                }
            }
        }
        vMeth_check_sum += i5 + i6 + i7 + Double.doubleToLongBits(d2) + i15 + i16 + Float.floatToIntBits(f1) + i17 +
            i18 + i19 + by + FuzzerUtils.checkSum(iArr1) + Double.doubleToLongBits(FuzzerUtils.checkSum(dArr));
    }

    public static long lMeth(int i1, boolean b, int i2) {

        double d=123.105719;
        int i3=-45198, i4=-46128, i20=95, i21=-163, i22=-11, i23=11, iArr[]=new int[N], iArr2[]=new int[N];
        byte by1=-97;

        FuzzerUtils.init(iArr, 56530);
        FuzzerUtils.init(iArr2, 14);

        iArr[(i2 >>> 1) % N] = (int)(d++);
        for (i3 = 7; i3 < 366; ++i3) {
            vMeth(i1);
            Test.instanceCount -= Test.iFld;
        }
        iArr2[(i3 >>> 1) % N] *= i2;
        d -= -16364;
        for (i20 = 15; 253 > i20; ++i20) {
            Test.iFld += i21;
            i21 += -9;
            i2 = i20;
            for (i22 = 1; 7 > i22; i22++) {
                iArr[i20 + 1] >>= i20;
            }
            Test.fFld = i2;
            iArr2[i20] -= (int)2.302F;
            by1 += (byte)(i20 * i2);
        }
        long meth_res = i1 + (b ? 1 : 0) + i2 + Double.doubleToLongBits(d) + i3 + i4 + i20 + i21 + i22 + i23 + by1 +
            FuzzerUtils.checkSum(iArr) + FuzzerUtils.checkSum(iArr2);
        lMeth_check_sum += meth_res;
        return (long)meth_res;
    }

    public void mainTest(String[] strArr1) {

        float f=-117.37F, f2=1.379F;
        int i=-8, i24=-14989, i25=58761, i26=223, i27=10, i28=-7, i29=-27415;
        short s1=-7371;
        double dArr1[]=new double[N];

        FuzzerUtils.init(dArr1, 0.11586);

        for (f = 6; f < 319; ++f) {
            Test.instanceCount >>>= ((-(25983 + lMeth(Test.iFld1, bFld, Test.iFld1))) * i);
        }
        Test.dFld += Test.iFld1;
        Test.instanceCount = Test.instanceCount;
        for (f2 = 15; f2 < 346; ++f2) {
            Test.instanceCount += i24;
            Test.iFld >>= Test.iFld1;
        }
        i -= (int)Test.instanceCount;
        for (i25 = 11; i25 < 319; i25++) {
            for (i27 = 1; i27 < 82; i27++) {
                i24 *= 4;
                i29 = 2;
                do {
                    if (bFld) {
                        if (false) {
                            Test.fFld += (40516L + (i29 * i29));
                        } else {
                            i26 += 3;
                        }
                        Test.iFld >>>= i29;
                        s1 = (short)Test.iFld1;
                        Test.iFld = 6129;
                    } else if (bFld) {
                        Test.iArrFld[i29 + 1] = i28;
                    } else if (bFld) {
                        Test.iFld += (i29 ^ Test.instanceCount);
                        if (bFld) break;
                        dArr1 = dArr1;
                    } else {
                        switch (((i25 % 5) * 5) + 37) {
                        case 46:
                            Test.iArrFld[i27] <<= (int)Test.instanceCount;
                            Test.iFld -= i29;
                            break;
                        case 56:
                            Test.iArrFld1[i29 - 1] += (int)Test.fFld;
                        case 49:
                            i <<= i27;
                            Test.iArrFld1[i29 - 1] += Test.iFld;
                            break;
                        case 39:
                            Test.iFld1 -= (int)1.167F;
                            break;
                        case 40:
                            i24 = i;
                            break;
                        }
                    }
                } while ((i29 -= 2) > 0);
            }
        }

        FuzzerUtils.out.println("f i f2 = " + Float.floatToIntBits(f) + "," + i + "," + Float.floatToIntBits(f2));
        FuzzerUtils.out.println("i24 i25 i26 = " + i24 + "," + i25 + "," + i26);
        FuzzerUtils.out.println("i27 i28 i29 = " + i27 + "," + i28 + "," + i29);
        FuzzerUtils.out.println("s1 dArr1 = " + s1 + "," + Double.doubleToLongBits(FuzzerUtils.checkSum(dArr1)));

        FuzzerUtils.out.println("Test.instanceCount Test.fFld Test.iFld = " + Test.instanceCount + "," +
            Float.floatToIntBits(Test.fFld) + "," + Test.iFld);
        FuzzerUtils.out.println("Test.iFld1 bFld Test.dFld = " + Test.iFld1 + "," + (bFld ? 1 : 0) + "," +
            Double.doubleToLongBits(Test.dFld));
        FuzzerUtils.out.println("Test.iArrFld Test.iArrFld1 = " + FuzzerUtils.checkSum(Test.iArrFld) + "," +
            FuzzerUtils.checkSum(Test.iArrFld1));

        FuzzerUtils.out.println("vMeth1_check_sum: " + vMeth1_check_sum);
        FuzzerUtils.out.println("vMeth_check_sum: " + vMeth_check_sum);
        FuzzerUtils.out.println("lMeth_check_sum: " + lMeth_check_sum);
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
//DEBUG  lMeth ->  lMeth mainTest
//DEBUG  vMeth ->  vMeth lMeth mainTest
//DEBUG  vMeth1 ->  vMeth1 vMeth lMeth mainTest
//DEBUG  Depth = 3
//DEBUG  Classes = 1
//DEBUG  static objects = {}
