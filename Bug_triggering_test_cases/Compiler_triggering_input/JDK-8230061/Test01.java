public class Test01 {
    public static final int N = 400;
    public static long instanceCount=-2288355609708559532L;
    public static double dFld=0.20487;
    public short sFld=-30484;
    public float fFld=2.319F;
    public static volatile float fArrFld[]=new float[N];
    public static volatile long lArrFld[]=new long[N];
    public static short sArrFld[]=new short[N];
    public static long vMeth_check_sum = 0;
    public static long vMeth1_check_sum = 0;
    public static long iMeth_check_sum = 0;
    public static int iMeth(double d1) {
        int i4=6022, i5=-211, i6=-15841, iArr[]=new int[N];
        float f1=0.641F;
        double d2=-8.78129, dArr[]=new double[N];
        Test01.fArrFld[(i4 >>> 1) % N] += 1;
        i5 = 1;
        do {
            i6 = 1;
            while (++i6 < 5) {
                i4 -= i6;
                i4 = -933;
                i4 += (i6 ^ (long)f1);
                i4 *= i4;
                dArr[i5 + 1] = i4;
                i4 -= i4;
                d2 = 1;
                do {
                    iArr[(int)(d2 + 1)] += (int)Test01.instanceCount;
                    try {
                        i4 = (i4 % -51430);
                        i4 = (iArr[i6] % 31311);
                        iArr[i6 + 1] = (24197 / i5);
                    } catch (ArithmeticException a_e) {}
                    i4 -= (int)Test01.instanceCount;
                    i4 <<= i5;
                    i4 &= 12;
                } while (++d2 < 1);
            }
        } while (++i5 < 320);
        long meth_res = Double.doubleToLongBits(d1) + i4 + i5 + i6 + Float.floatToIntBits(f1) +
            Double.doubleToLongBits(d2);
        iMeth_check_sum += meth_res;
        return (int)meth_res;
    }
    public static void vMeth1(double d, int i1) {
        float f=0.805F;
        int i2=49870, i3=53077;
        short s=-8651;

        f -= (((++Test01.instanceCount) + i1) + -61540);
        for (i2 = 11; 331 > i2; ++i2) {
            i1 &= iMeth(0.8522);
            Test01.instanceCount = s;
        }
        vMeth1_check_sum += Double.doubleToLongBits(d) + i1 + Float.floatToIntBits(f) + i2 + i3 + s;
    }
    public static void vMeth(int i) {
        int i7=13, i8=-50, i9=60, i10=-16966, i11=-23874, iArr1[]=new int[N];
        float f2=77.768F;
        double d3=-108.86742, dArr1[]=new double[N];
        vMeth1(Test01.dFld, i);
        for (long l : Test01.lArrFld) {
            i = i;
            i = i;
            for (i7 = 1; i7 < 4; ++i7) {
                dArr1[i7] *= f2;
                i8 = i8;
                d3 = 1;
                do {
                    i9 <<= (int)l;
                    l -= Test01.instanceCount;
                } while (++d3 < 2);
                for (i10 = i7; i10 < 2; i10++) {
                    i8 += i10;
                    iArr1[i10 + 1] = i;
                    f2 += (((i10 * i7) + i) - i9);
                    i11 = (int)Test01.instanceCount;
                }
            }
        }
        vMeth_check_sum += i + i7 + i8 + Float.floatToIntBits(f2) + Double.doubleToLongBits(d3) + i9 + i10 + i11;
    }
    public void mainTest01(String[] strArr1) {

        int i12=19342, i13=11250, i14=-248, i15=-43625, i16=-12, i17=16657, i18=-247, i19=71, i20=-133, iArr2[][]=new
            int[N][N];
        double d4=-59.56679;
        boolean b=true;
        byte by=16;
        long l1=-24907L;
        vMeth(i12);
        d4 = 197;
        do {
            sFld = (short)i12;
        } while ((d4 -= 2) > 0);
        i12 >>= i12;
        for (i13 = 15; i13 < 366; i13++) {
            for (i15 = 1; 72 > i15; i15++) {
                b = b;
                Test01.instanceCount = i15;
                try {
                    i14 = (726415012 % i16);
                    iArr2[i13][i15] = (16104697 / iArr2[i13 + 1][i13]);
                    i12 = (117 % iArr2[i13 - 1][i15]);
                } catch (ArithmeticException a_e) {}
                iArr2[i15 + 1][i13] = (int)Test01.instanceCount;
                i14 -= (int)fFld;
            }
            i14 -= i15;
            by += (byte)i13;
            Test01.sArrFld[i13 - 1] = (short)Test01.instanceCount;
            Test01.instanceCount %= (i14 | 1);
            iArr2[i13 - 1] = iArr2[i13];
            i17 += i14;
            fFld = Test01.instanceCount;
        }
        Test01.dFld = Test01.instanceCount;
        i16 += i17;
        i12 *= i15;
        i14 &= by;
        for (l1 = 10; l1 < 295; ++l1) {
            Test01.instanceCount >>= i13;
            Test01.instanceCount = -89;
            for (i19 = 5; 88 > i19; ++i19) {
                iArr2[i19][i19 + 1] = (int)d4;
                i14 -= i19;
            }
            by >>= (byte)i17;
            i12 -= (int)1113313957L;
        }
    }
    public static void main(String[] strArr) {

        try {
            Test01 _instance = new Test01();
            for (int i = 0; i < 10; i++ ) {
                _instance.mainTest01(strArr);
            }
         } catch (Exception ex) {
            ;
         }
    }
}

