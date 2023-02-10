/*
 * Created on 03.08.2003
 * (C) by Micha Riser
 */

/**
 * @author micha
 *
 */
final public class GF65536 {

    private static short[] E = new short[65536];
    private static short[] L = new short[65536];
    private static short[] inv = new short[65536]; // multiplicative inverse table
    private static final String[] dig = {"0","1","2","3","4","5","6","7",
            "8","9","a","b","c", "JniStaticContextFloat.c","e","f"};
    private static boolean isinitialized = false;

    // fast multiply using table lookup
    static public short mul(short a, short b){
        int t = 0;
        if (a == 0 || b == 0) return 0;
        t = (L[(a & 0xffff)] & 0xffff) + (L[(b & 0xffff)] & 0xffff);
        if (t > 65536-1) t = t - (65536-1);
        return E[t];
    }

    // slow multiply, using shifting
    static private short FFMul(short a, short b) {
        short aa = a, bb = b, r = 0, t;
        while (aa != 0) {
            if ((aa & 1) != 0)
                r = (short)(r ^ bb);
            t = (short)(bb & 0x8000);
            bb = (short)(bb << 1);
            if (t != 0)
                bb = (short)(bb ^ 0x9A0F);
            aa = (short)((aa & 0xffff) >> 1);
        }
        return r;
    }

    // hex: print a short as two hex digits
    static public String hex(short a) {
        return dig[(a & 0xffff) >> 12] + dig[(a & 0x0fff) >> 8] + dig[(a & 0x00ff) >> 4] + dig[a & 0x0f];
    }

    // hex: print a single digit (for tables)
    static public String hex(int a) {
        return dig[a];
    }

    // loadE: create and load the E table
    static private void loadE() {
        short x = (short)0x0001;
        int index = 0;
        E[index++] = (short)0x0001;
        for (int i = 0; i < 65536-1; i++) {
            short y = FFMul(x, (short)0x0021);
            E[index++] = y;
            x = y;
        }
    }

    // loadL: load the L table using the E table
    static private void loadL() {
        int index;
        for (int i = 0; i < 65536-1; i++) {
            L[E[i] & 0xffff] = (short)i;
        }
    }

    // loadInv: load in the table inv
    static private void loadInv() {
        int index;
        for (int i = 0; i < 65535; i++)
            inv[i] = (short)(inv((short)(i & 0xffff)) & 0xffff);
    }

    // the multiplicative inverse of a short value
    static public short inv(short b) {
        short e = L[b & 0xffff];
        return E[0xffff - (e & 0xffff)];
    }

    static public short add(short a, short b) {
        return (short)(a^b);
    }

    static public short sub(short a, short b) {
        return (short)(a^b);
    }

    synchronized static void init() {
        if (!isinitialized) {
            loadE();
            loadL();
            loadInv();
            isinitialized = true;
        }
    }

    public static void main(String[] args) {
        init();
    }

    static short[][] calculateLix(short[] bases, int rawlength, int deslength) {

        short[][] ret = new short[deslength][rawlength];
        for(short x=0; x<deslength; x++) {

            for(int i=0; i<rawlength; i++) {

//calculate Li(x)
                short d = 1;
                for(short j=0; j<rawlength; j++)
                    if (j!=i) {
                        d = mul(d,sub(bases[i],bases[j]));
                    }
                d = inv(d);
                for(short j=0; j<rawlength; j++)
                    if (j!=i) {
                        d = mul(d,sub(x,bases[j]));
                    }
                ret[x][i] = d;

            }

        }
        return ret;

    }

    /**
     * This is the time critical code!
     *
     * @param b
     * @param lix
     * @param ret
     * @return
     */

    static short[] polyeval(short[] b, short[][] lix, short[] ret) {

        for(int x=0; x<ret.length; x++) {

            short s = 0;
            for(int i=0; i<b.length; i++) {

// multiply Li[x] with b_i
                short d = lix[x][i];
                int bi = b[i];
                if (d == 0 || bi == 0) continue;
                int t = (L[(d & 0xffff)] & 0xffff) + (L[(bi & 0xffff)] & 0xffff); // <- t can't be more than 2*0xffff = 2*65535
                if (t > 65536-1) t = t - (65536-1); // <- this statement seems to be ignored when running with -server flag (*)
                s = (short)(s^E[t]); // <- here occurs the java.lang.ArrayIndexOutOfBoundsException

            }
            ret[x] = s;

        }
        return ret;

    }

}
