public class Test {
    public static final int N = 400;
    public static long instanceCount=-7L;
    public short sFld=20545;
    public static int iFld=-4;
    public static boolean bFld=true;
    public static float fFld=-2.11F;
    public static byte byFld=-79;
    public int iArrFld[][]=new int[N][N];
    public static long lArrFld[]=new long[N];
    public static long vMeth_check_sum = 0;
    public static long vMeth1_check_sum = 0;
    public static long sMeth_check_sum = 0;
    public static short sMeth() {
        byte by=21;
        int i4=3, i5=6, i6=-141, i7=-250, i8=8;
        float f=2.443F;
        double d3=0.88279;
        long lArr[]=new long[N];
        lArr[(Test.iFld >>> 1) % N] = (long)2.21403;
        Test.instanceCount = by;
        for (i4 = 5; 135 > i4; ++i4) {
            f += i4;
            Test.iFld = (int)d3;
        }
        f -= by;
        i6 = 1;
        do {
            by = (byte)i5;
            if (i6 != 0) {
            }
            Test.bFld = Test.bFld;
            Test.instanceCount = i6;
            Test.iFld ^= 80;
        } while (++i6 < 341);
        for (i7 = 2; i7 < 129; i7 += 2) {
            i8 = i6;
            i5 = i7;
        }
        long meth_res = by + i4 + i5 + Float.floatToIntBits(f) + Double.doubleToLongBits(d3) + i6 + i7 + i8 ;
        sMeth_check_sum += meth_res;
        return (short)meth_res;
    }

    public static long checksumFun(int [] iArr) {
      long ArrSum = 0;
      for (int i = 0 ; i < N; i++) {
        ArrSum += iArr[i];
      }
      return (ArrSum / N);
    }

    public static long lchecksumFun(long [] iArr) {
      long ArrSum = 0;
      for (int i = 0 ; i < N; i++) {
        ArrSum += iArr[i];
      }
      return (ArrSum / N);
    }

    public static void vMeth1(int i2) {
        double d2=67.29039;
        int i3=2885, i9=11, i10=15570, i11=206, i12=-64611, i13=-17, i14=133, iArr[]=new int[N];
        short s=21444;
        long lArr1[]=new long[N];
        i2 = i2;
        for (d2 = 7; d2 < 248; d2 += 2) {
            i3 -= iArr[(int)(d2 + 1)];
            i3 -= (int)((d2 * sMeth()) * Test.instanceCount);
            for (i9 = 1; i9 < 13; i9++) {
                iArr[(int)(d2 - 1)] = s;
            }
            for (i11 = 13; i11 > 1; --i11) {
                for (i13 = 1; 2 > i13; i13++) {
                    if (Test.bFld) continue;
                    iArr[i13] = (int)-2535463683L;
                    Test.iFld -= (int)Test.fFld;
                    Test.instanceCount -= 24433;
                    i3 = -7;
                    iArr[i11 - 1] = i2;
                    lArr1[i11 + 1] += i14;
                }
            }
        }
        vMeth1_check_sum += i2 + Double.doubleToLongBits(d2) + i3 + i9 + i10 + s + i11 + i12 + i13 + i14 +
            checksumFun(iArr) + lchecksumFun(lArr1);

    }

    public static void vMeth() {
        int i16=1304, i17=3898, i18=-162, i19=15944, iArr1[]=new int[N];
        long l=-7691034891179829652L, lArr2[]=new long[N];
        vMeth1(Test.iFld);
        Test.instanceCount <<= Test.iFld;
        for (int i15 : iArr1) {
            for (i16 = 4; i16 > 1; i16--) {
                for (l = 1; l < 2; ++l) {
                    i17 -= (int)l;
                    Test.iFld = Test.iFld;
                    if (Test.bFld) {
                        lArr2[i16] *= i17;
                    }
                    Test.instanceCount <<= -7430;
                    if (Test.iFld != 0) {
                        vMeth_check_sum += i16 + i17 + l + i18 + i19;
                        return;
                    }
                }
                iArr1[i16] -= (int)l;
                i18 /= (int)(Test.iFld | 1);
                Test.fFld -= i19;
                i19 -= i19;
            }
        }
      vMeth_check_sum += i16 + i17 + l + i18 + i19;
    }

    public void mainTest(String[] strArr1) {
        double d=-1.59026, dArr[]=new double[N];
        int i=227, i1=-165, i20=156, i21=-2330, i22=-5873;
        sFld = (short)d;
        for (i = 15; i < 327; ++i) {
            vMeth();
            Test.iFld -= (int)Test.instanceCount;
            Test.iFld -= i1;
            Test.iFld = (int)Test.instanceCount;
            switch (((i % 5) * 5) + 20) {
            case 45:
                Test.iFld += i;
                i20 = 1;
                do {
                    for (i21 = i20; i21 < 1; ++i21) {
                        i22 += (i21 * i21);
                        iArrFld[i21][i21] -= i20;
                        i22 *= Test.iFld;
                        d = i20;
                        Test.instanceCount += (i21 * i21);
                        switch ((i20 % 6) + 72) {
                        case 72:
                            Test.iFld += i21;
                            iArrFld[i - 1][i21] = (int)187L;
                            Test.instanceCount |= i20;
                            break;
                        case 73:
                            if (Test.bFld) break;
                            break;
                        case 74:
                            Test.byFld <<= (byte)Test.iFld;
                            i22 += i21;
                            break;
                        case 75:
                            Test.fFld += (((i21 * i22) + i22) - i1);
                            switch (((i % 1) * 5) + 11) {
                            case 15:
                                Test.iFld = (int)-822381893L;
                                iArrFld[i][i21 - 1] -= Test.iFld;
                                break;
                            }
                            break;
                        case 76:
                            i1 += i21;
                            Test.instanceCount += i20;
                            break;
                        case 77:
                            iArrFld[i - 1] = iArrFld[i + 1];
                            i22 <<= (int)Test.instanceCount;
                            break;
                        }
                    }
                } while (++i20 < 81);
                break;
            case 24:
                Test.instanceCount <<= Test.iFld;
                break;
            case 37:
                Test.lArrFld[i + 1] = i;
                break;
            case 26:
                iArrFld[i + 1][i - 1] = i22;
                break;
            case 23:
                Test.fFld *= i20;
            default:
                Test.bFld = false;
            }
        }
    }
    public static void main(String[] strArr) {
        try {
            Test _instance = new Test();
            for (int i = 0; i < 10; i++ ) {
                _instance.mainTest(strArr);
            }
         } catch (Exception ex) {
            ;
         }
    }
}

