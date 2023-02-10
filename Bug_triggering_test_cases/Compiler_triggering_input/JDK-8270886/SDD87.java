public class SDD87 {
    public volatile double do0 = -2534119863.3824302206 + 3552129807.3223861824;
    public static long lo1[][] = new long[1707][245];
    
    static {
        try {
        FuzzerUtils.init(SDD87.lo1, 1674754604L);
        } catch (Exception ex) {
            System.out.println(ex.getClass().getCanonicalName());
        }
    }
    
    public long m2 (double a_do0, byte a_by1){
        double do3 = -2074076350.2364243247;
        int in4 = 129;
        do3 = do3;
        return -3480877547L;
    }
    
    public static void m5 (byte a_by0, float a_fl1){
        float fl6 = 2906541500.3343507878f - -448260043.513218082f;
        byte by7 = (byte)0;
    }
    
    public static void m8 (byte a_by0, double a_do1){
        byte by9 = (byte)8;
        short sh10 = (short)11;
        boolean bo11 = false;
        int in12 = 4;
        m5((byte)3, 849311189.936438685f);
        for (int i13 = 0; i13 < 68; i13++) {
            m5((byte)16, 3821036031.1432977877f);
            m5((byte)((byte)-15 + (byte)10), 725736122.1877951484f);
            m5((byte)((byte)5 - (byte)7), -237458084.30816868f);
        }
        m5((byte)((byte)14 + (byte)7), 3497367408.1085204991f);
        m5((byte)15, 2756248178.3128363607f);
    }
    public void mainTest (String[] args){
        byte by14 = (byte)9;
        long lo15 = 1502432357L;
        short sha16[] = new short[473];
        
        try {
        FuzzerUtils.init(sha16, (short)((short)52));
        } catch (Exception ex) {
            System.out.println(ex.getClass().getCanonicalName());
        }
        for (short sh17 : sha16) {
            try {
                sha16[2] = (short)23;
                for (int i18 = 0; i18 < 17; i18++) {
                    sha16[i18] = sh17;
                    m2(1120187380.53448312, by14);
                }
            } catch (ArithmeticException a_e) {}
            sha16[1] = sh17;
            sha16[2] = sh17;
        }
        m2(825278101.1289499682, by14);
        by14 = (byte)(by14 - by14);
        for (int i19 = 0; i19 < 20; i19++) {
            if (i19 == i19) {
                sha16[i19] *= (short)-24;
                i19 = i19;
            }
            for (short sh20 : sha16) {
                for (int i21 = 0; i21 < 83; i21++) {
                    sha16[0] -= (short)-46;
                    i19 += 127;
                }
                for (int i22 = 0; i22 < 89; i22++) {
                    i22 = 8;
                }
            }
            ;
        }
        ;
        ;
        for (int i23 = 0; i23 < 33; i23++) {
            sha16[i23] = (short)46;
            for (int i24 = 0; i24 < 94; i24++) {
                sha16[2] = (short)-3;
            }
        }
        sha16[0] = (short)14;
        for (int i25 = 0; i25 < 35; i25++) {
            for (short sh26 : sha16) {
                if (i25 > 5) {
                    m2(1121925038.1118634045, by14);
                }
            }
            m2(-1914069692.1375346593, (byte)16);
        }
        ;
        for (int i27 = 0; i27 < 10; i27++) {
            for (int i28 = 0; i28 < 44; i28++) {
                if (i28 == i28) {
                    break;
                }
                sha16[i27] = (short)62;
                for (int i29 = 0; i29 < 95; i29++) {
                    sha16[2] = (short)50;
                    sha16[2] = (short)30;
                    m2(1250986231.1599386644, by14);
                }
            }
            sha16[2] = (short)28;
            m2(672818118.3111172289, (byte)0);
        }
        sha16[0] = (short)19;
        sha16[5] = (short)((short)-11 * (short)60);
        for (int i30 = 0; i30 < 38; i30++) {
            sha16[i30] = (short)43;
            sha16[i30] = (short)49;
            try {
                for (short sh31 : sha16) {
                    m2(720645491.3777510146, by14);
                    sha16[i30] -= (short)-44;
                    sha16[i30] = (short)58;
                }
                for (short sh32 : sha16) {
                    m2(-3548500610.1703635180 % -2696439975.1456774235 % 2299457624.855537726, by14);
                    sha16[2] = (short)12;
                    sha16[i30] = (short)8;
                }
                i30 = 4;
            } catch (ArithmeticException a_e) {}
        }
        for (int i33 = 0; i33 < 31; i33++) {
            for (int i34 = 0; i34 < i33; i34++) {
                try {
                    sha16[i34] /= (short)-25;
                    i34 = -65536;
                    i33 = i33;
                } catch (ArithmeticException a_e) {}
                for (short sh35 : sha16) {
                    sha16[i34] += sha16[i33];
                    sha16[i34] = sh35;
                    sha16[i33] = (short)15;
                }
                for (short sh36 : sha16) {
                    m2(3573835015.2140351447, by14);
                    m2(2984270380.1830267895, by14);
                }
            }
            sha16[i33] = (short)57;
            m2(-3061961160.3118322232, (byte)-4);
        }
        sha16[256] -= (short)50;
    }
    public static void main(String[] args) {
        try {
            SDD87 instance = new SDD87();
            for (int i = 0; i < 100; ++i) {
                instance.mainTest(args);
            }
        } catch (Exception ex) {
            System.out.println(ex.getClass().getCanonicalName());
        }
    }
}