public class SDD56 {
    public static float fl0[] = new float[1089];
    public boolean bo1[][] = new boolean[142][110];
    public int in2[][] = new int[1106][118];
    
    static {
        try {
        FuzzerUtils.init(SDD56.fl0, 1552888667.95472357f);
        } catch (Exception ex) {
            System.out.println(ex.getClass().getCanonicalName());
        }
    }
    
    public boolean m3 (long a_lo0, short a_sh1){
        long lo4 = 820167L;
        short sh5 = (short)-32;
        long loa6[][] = new long[560][1792];
        
        try {
        FuzzerUtils.init(loa6, -1297708728L);
        } catch (Exception ex) {
            System.out.println(ex.getClass().getCanonicalName());
        }
        loa6[-128][0] = 1345348328L;
        ;
        return false;
    }
    
    public void m7 (boolean a_bo0, byte a_by1){
        boolean boa8[][] = new boolean[661][394];
        short sha9[] = new short[1459];
        
        try {
        FuzzerUtils.init(boa8, false);
        FuzzerUtils.init(sha9, (short)((short)7));
        } catch (Exception ex) {
            System.out.println(ex.getClass().getCanonicalName());
        }
        m3(3575205087L, (short)41);
    }
    
    public short m10 (byte a_by0, short a_sh1){
        byte by11 = (byte)((byte)3 + (byte)(a_by0 - a_by0));
        float fl12 = 3944042650.1188799204f;
        byte by13 = (byte)1;
        int ina14[][] = new int[1696][724];
        
        try {
        FuzzerUtils.init(ina14, 65537);
        } catch (Exception ex) {
            System.out.println(ex.getClass().getCanonicalName());
        }
        m7(false, (byte)(a_by0 - (byte)14));
        ina14[0][127] = 4;
        m3(3720271546L, (short)18);
        return (short)17;
    }
    public void mainTest (String[] args){
        double do15 = 3170743624.2553528459;
        short sh16 = (short)-32;
        for (int i17 = 0; i17 < 29; i17++) {
            if (i17 == 8) {
                if (i17 < -65535) {
                    sh16 = sh16;
                    do15 = -2164060449.3256487478;
                    do15 = -2663951633.3797941189;
                }
                i17 = 129;
                for (int i18 = 0; i18 < 27; i18++) {
                    do15 = -2995124950.4103460110;
                    i18 = 16;
                }
            }
        }
        m10((byte)6, (short)28);
        try {
            m3(4130511410L, (short)(sh16 % sh16));
            do {
                do15 = 3956630619.3058117863;
                sh16 = sh16;
                sh16 = sh16;
            } while (1<0);
        } catch (ArithmeticException a_e) {}
        m7(false, (byte)11);
        do15 = do15;
        try {
            do15 = 4220411147.1766418322;
            sh16 = (short)13;
        } catch (ArithmeticException a_e) {}
        try {
            do15 = -1767552913.2112160402;
        } catch (ArithmeticException a_e) {}
        try {
            do15 = do15;
            sh16 = (short)-37;
        } catch (ArithmeticException a_e) {}
        do15 = do15;
        sh16 = (short)22;
        for (int i19 = 0; i19 < 77; i19++) {
            for (int i20 = 0; i20 < 33; i20++) {
                for (int i21 = 0; i21 < i19; i21++) {
                    m3(4003070808L, sh16);
                }
                m7(true, (byte)3);
                sh16 = (short)-44;
            }
        }
        sh16 = (short)48;
        m10((byte)15, sh16);
        sh16 = (short)55;
        do15 = 81938634.2373799808;
        sh16 *= sh16;
        sh16 -= sh16;
    }
    public static void main(String[] args) {
        try {
            SDD56 instance = new SDD56();
            for (int i = 0; i < 100; ++i) {
                instance.mainTest(args);
            }
        } catch (Exception ex) {
            System.out.println(ex.getClass().getCanonicalName());
        }
    }
}