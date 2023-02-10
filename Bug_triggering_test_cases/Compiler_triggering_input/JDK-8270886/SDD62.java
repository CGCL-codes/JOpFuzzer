public class SDD62 {
    public static boolean bo0 = true;
    public double do1[] = new double[998];
    public static int in2[] = new int[205];
    public static byte by3[][] = new byte[1233][1035];
    
    static {
        try {
        FuzzerUtils.init(SDD62.in2, 534);
        FuzzerUtils.init(SDD62.by3, (byte)((byte)10));
        } catch (Exception ex) {
            System.out.println(ex.getClass().getCanonicalName());
        }
    }
    
    public void m4 (long a_lo0, float a_fl1){
        boolean bo5 = false;
        double do6 = -718243720.4284930944;
        float fla7[] = new float[715];
        
        try {
        FuzzerUtils.init(fla7, -3209792248.2764405256f);
        } catch (Exception ex) {
            System.out.println(ex.getClass().getCanonicalName());
        }
        for (int i8 = 0; i8 < 97; i8++) {
            if (i8 == i8) {
                fla7[i8] = -172883965.1745583736f;
            }
            fla7[i8] = a_fl1;
            fla7[2] += -3643268607.2067792258f;
        }
        for (int i9 = 0; i9 < 56; i9++) {
            fla7[0] = 1043246547.2919369130f - -3872325199.1617164639f;
            ;
            ;
        }
    }
    
    public static int m10 (float a_fl0, int a_in1){
        float fl11 = -2828158166.3209725388f + 3489271951.1776073077f;
        double do12 = -4018532442.350975891;
        byte bya13[] = new byte[923];
        
        try {
        FuzzerUtils.init(bya13, (byte)((byte)-12));
        } catch (Exception ex) {
            System.out.println(ex.getClass().getCanonicalName());
        }
        System.out.println("Hi");
        return 255;
    }
    
    public void m14 (long a_lo0, boolean a_bo1){
        byte by15 = (byte)5;
        int ina16[] = new int[743];
        byte bya17[][] = new byte[262][427];
        float fla18[] = new float[1718];
        
        try {
        FuzzerUtils.init(ina16, 653);
        FuzzerUtils.init(bya17, (byte)((byte)3));
        FuzzerUtils.init(fla18, -1678863219.2138523828f);
        } catch (Exception ex) {
            System.out.println(ex.getClass().getCanonicalName());
        }
    }
    
    public void m19 (short a_sh0, short a_sh1){
        boolean boa20[][] = new boolean[660][1619];
        boolean bo21 = false;
        boolean boa22[][] = new boolean[1812][1496];
        double do23 = -906170301.2002545999;
        
        try {
        FuzzerUtils.init(boa20, true);
        FuzzerUtils.init(boa22, false);
        } catch (Exception ex) {
            System.out.println(ex.getClass().getCanonicalName());
        }
        boa22[1][128] = true;
        boa22[4096][1] = true;
        boa20[1][1] = bo21;
        for (int i24 = 0; i24 < 99; i24++) {
            try {
                for (int i25 = 0; i25 < i24; i25++) {
                    boa20[2][i25] = true;
                }
                for (int i26 = 0; i26 < 93; i26++) {
                    boa22[1][0] = bo21;
                    m14(3117172930L, true);
                    boa22[i26][i26] = false;
                }
            } catch (ArithmeticException a_e) {}
            if (i24 < 1) {
                boa20[i24][i24] = false;
                for (int i27 = 0; i27 < 44; i27++) {
                    boa20[i27][2] = true;
                }
                if (i24 == i24) {
                    break;
                }
            }
            boa20[i24][i24] = false;
        }
    }
    public void mainTest (String[] args){
        short sh28 = (short)((short)8 + (short)0);
        long lo29 = 199557392L;
        for (int i30 = 0; i30 < 51; i30++) {
            i30 = i30;
            sh28 = (short)8;
            if (i30 == 8) {
                try {
                    i30 = 256;
                } catch (ArithmeticException a_e) {}
                if (i30 == i30) {
                    lo29 = lo29;
                }
                for (int i31 = 0; i31 < 40; i31++) {
                    i31 = i31;
                    i31 = 10;
                }
            }
        }
        for (int i32 = 0; i32 < 35; i32++) {
            try {
                if (i32 > i32) {
                    i32 %= 129;
                    i32 = i32;
                }
            } catch (ArithmeticException a_e) {}
        }
        sh28 = sh28;
        try {
            lo29 = lo29;
        } catch (ArithmeticException a_e) {}
        m14(lo29, true);
        sh28 = (short)-55;
        for (int i33 = 0; i33 < 12; i33++) {
            try {
                i33 = i33;
                i33 = i33;
                for (int i34 = 0; i34 < i33; i34++) {
                    m19((short)((short)-63 / sh28), (short)32);
                    i34 = 512;
                }
            } catch (ArithmeticException a_e) {}
        }
        lo29 = lo29;
    }
    public static void main(String[] args) {
        try {
            SDD62 instance = new SDD62();
            for (int i = 0; i < 100; ++i) {
                instance.mainTest(args);
            }
        } catch (Exception ex) {
            System.out.println(ex.getClass().getCanonicalName());
        }
    }
}