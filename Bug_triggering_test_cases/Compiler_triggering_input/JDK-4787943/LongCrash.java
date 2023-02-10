/*
 * This example detects errant arithmetic in the sizeAdj() method.
 * The error occurs on Solaris Sparc JVM 1.4.0 and 1.4.1_01 when
 * running with the java -client command line argument. This program
 * works as expected on 1.3.1_01. The J2SE_Solaris_8_Recommended
 * patches were all installed. This program also works on the 1.4
 * versions when run with the -server command line argument.
 *
 * The failure occurs in the long shifts in the sizeAdj() method. The
 * error occurs for numbers sligthly larger than MIN_LONG and doesn't
 * always fail on the same number which might be related to how long
 * the program runs before the sizeAdj() method is dynamically
 * optimized. The error is detected by checking the result of the
 * sizeAdj() method against the input value; they should be identical
 * since the shifts performed in sizeAdj() should always be by 0 since
 * the width argument is set to a constant 64 when sizeAdj() is
 * called.
 *
 * Tinkering with the program has showed that the error doesn't occur
 * when run with -server or if the sizeAdj() local variable "shiftMag"
 * is declared as long (causing the shift amount calculation to
 * produce a long instead of the integer value as coded) or if the
 * "shiftMag" variable declaration is left as an integer but moved
 * after the "ret" variable declaration. The program will also work
 * if the "shiftMag" variable is removed and replaced with its only
 * value of the "width" variable, or if the shift amount is forced to
 * be a long value by using "64L" in the amount calculation.
 *
 * To reproduce the error:
 *
 * 1. javac LongCrash.java
 * 2. java -client LongCrash
 *
 * The output will be similar to:
 *
 * ERROR: sent: 80000000000003d7 got: ffffff00000003d7
 *
 */
public class LongCrash
{
    public static void main(String[] args)
    {
        while(true)
        {
            for(long i=0x7ffffffffffffff0L; i != 0x80000000000f0000L; i++)
            {
                long answer = 0L;

                if((answer = sizeAdj(i,64)) != i)
                {
                    System.out.println("ERROR: sent: " +
                            Long.toHexString(i) + " got: " +
                            Long.toHexString(answer));
                    System.exit(1);
                }
            }
        }
    }

    private static long sizeAdj (long value, int width)
    {
        // if this variable declaration moves after the next one it
        // works
        // if this variable declaration stays here but is made long it works
        int shiftMag = width;

        long ret = value;

        // if the shiftMag variable is replaced with width it works
        // if the constant 64 is replaced with 64L it works
        ret <<= (64 - shiftMag);
        ret >>= (64 - shiftMag);

        return ret;
    }
}