import jdk.incubator.vector.*;


public class TestFloatVector {
    static final int LENGTH = 160;
    static final int WARMUP_CNT = 1000;
    static final int LOOP_CNT = 1000000;
    static float [] a = new float[LENGTH];;
    static float [] b = new float[LENGTH];
    static float [] c = new float[LENGTH];
    static float [] r = new float[LENGTH];
    static boolean [] m = new boolean[LENGTH];

    static final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_128;

    public static void TestInit() {
        int vl = SPECIES.length();
        for (int i = 0; i < LENGTH; i += vl) {
            for (int j = 0; j < vl; j++) {
                a[i+j] = 1.0f;
                b[i+j] = 2.0f;
                c[i+j] = 3.0f;
                if (j % 2 == 0) {
                  m[i+j] = true;
                } else {
                  m[i+j] = false;
                }                
            }
        }
    }

    static void fmaTest(float[] a, float[] b, float[] c, float[] r, boolean[] m) {
        VectorMask<Float> vmask = VectorMask.fromArray(SPECIES, m, 0);

        for (int ic = 0; ic < 100; ic++) {
            for (int i = 0; i < a.length; i += SPECIES.length()) {
                FloatVector av = FloatVector.fromArray(SPECIES, a, i);
                FloatVector bv = FloatVector.fromArray(SPECIES, b, i);
                FloatVector cv = FloatVector.fromArray(SPECIES, c, i);
                FloatVector fv = av.lanewise(VectorOperators.FMA, bv, cv, vmask);
                fv.intoArray(r, i);
            }
        }

    }

    public static void main(String [] args) {
        boolean verify = false;
        long start, end;

        TestInit();

        for (int i = 0; i < WARMUP_CNT; i++) {
            fmaTest(a, b, c, r, m);
        }
        for (int i = 0; i < 8; i++) {
            System.out.println("r = " + r[i]);
        }
    }

}
