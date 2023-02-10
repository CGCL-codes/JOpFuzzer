import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Random;

/**
 * The FloatBufferTest demonstrates an error in the FloatBuffer when executed
 * with the Java 6 jvm with the jvm arg '-server'. If the Java 6 jvm is executed
 * in client mode, then there is no error.
 * <p>
 * Also worth noting is that the Java 1.4 and Java 5 jvms execute the code
 * without any errors for both jvm args '-server' and '-client'.
 * <p>
 * To see the errors execute the code below using the Java 6 jvm with '-server'
 * specified.
 * <p>
 * example: java -server FloatBufferTest
 * <p>
 * Execute the same code using the same Java 6 jvm without the '-server' option
 * and no errors are produced.
 * <p>
 * example: java FloatBufferTest or java -client FloatBufferTest
 * <p>
 * Java version: Java version 1.6.0_01-b06
 * <p>
 * Misc: The IntBuffer and DoubleBuffer are also tested in the same manner that
 * the FloatBuffer is tested and no errors have been seen using the same scenario.
 * Perhaps execution order of the methods may affect the outcome (not tested).
 */
public class FloatBufferTest {


    private static int NUM_LOOPS = 1000;

    //change to true to dump error values to console
//note that doing so may affect results
    private static boolean DUMP_ERROR_VALUES = false;

    public static void main(String[] args) {
        long seed = 8675309;
        int dlength = 50000; // 50000 entries

        FloatBufferTest.testFloatBuffer(seed, dlength);
//test DoubleBuffer in the same manner
        FloatBufferTest.testDoubleBuffer(seed, dlength);
//test IntBuffer in the same manner
        FloatBufferTest.testIntBuffer(seed, dlength);

//dump some jvm info
        String jvmversion = System.getProperty("java.vm.version");
        String jvmname = System.getProperty("java.vm.name");
        System.err.println("Java version " + jvmversion);
        System.err.println("Java name " + jvmname);

    }

    /**
     * Test the FloatBuffer
     *
     * @param pSeed   the seed value
     * @param pLength the number of data values
     */
    protected static void testFloatBuffer(long pSeed, int pLength) {

        int blength = 4; // 4 btyes
        float[] floats = new float[pLength];

        Random r = new Random(pSeed);
//populate src float array
        for (int i = 0; i < pLength; i++) {
            floats[i] = r.nextFloat();
        }


        int loopErrorCount = 0;
        int firstLoopToErr = -1;


        for (int ii = 0; ii <= NUM_LOOPS; ii++) {

            ByteBuffer destbb = ByteBuffer.allocate(pLength * blength);
            FloatBuffer fb = destbb.asFloatBuffer();
            fb.put(floats, 0, pLength);

            int errorCount = 0;
//compare source float values to those copied into the FloatBuffer
            for (int i = 0; i < floats.length; i++) {
                float fbfvalue = fb.get(i);
                float srcvalue = floats[i];
//check for mismatching values
                if (fbfvalue != srcvalue) {
                    errorCount++;

                    if (DUMP_ERROR_VALUES) {
                        System.err.println("*** Data values not equal error");
                        System.err.println(i + " (array index) Expected " + srcvalue
                                + ", got " + fbfvalue);
                    }

                }
            } //end i

//check if there where any errors in loop execution
            if (errorCount > 0) {
//track the first loop to error
                if (firstLoopToErr == -1) {
                    firstLoopToErr = ii;
                }
                loopErrorCount++;
                System.err
                        .println("Error count for the current loop in comparing FloatBuffer to float src array "
                                + errorCount);
            }


        }// end ii

        System.err.println(loopErrorCount + " loops experienced errors. Total FloatBuffer loops executed " + NUM_LOOPS);
        if (loopErrorCount > 0) {
            System.err.println("First FloatBuffer loop to error: " + firstLoopToErr);
        }

    }

    /**
     * Test the DoubleBuffer
     *
     * @param pSeed   the seed value
     * @param pLength the number of data values
     */
    protected static void testDoubleBuffer(long pSeed, int pLength) {

        int blength = 8; // 8 btyes
        double[] src = new double[pLength];

        Random r = new Random(pSeed);
//populate src array
        for (int i = 0; i < pLength; i++) {
            src[i] = r.nextDouble();
        }


        int loopErrorCount = 0;
        int firstLoopToErr = -1;


        for (int ii = 0; ii <= NUM_LOOPS; ii++) {

            ByteBuffer destbb = ByteBuffer.allocate(pLength * blength);
            DoubleBuffer fb = destbb.asDoubleBuffer();
            fb.put(src, 0, pLength);

            int errorCount = 0;
//compare source float values to those copied into the DoubleBuffer
            for (int i = 0; i < src.length; i++) {
                double fbfvalue = fb.get(i);
                double srcvalue = src[i];
//check for mismatching values
                if (fbfvalue != srcvalue) {
                    errorCount++;

                    if (DUMP_ERROR_VALUES) {
                        System.err.println("*** Data values not equal error");
                        System.err.println(i + " (array index) Expected " + srcvalue
                                + ", got " + fbfvalue);
                    }

                }
            } //end i

//check if there where any errors in loop execution
            if (errorCount > 0) {
//track the first loop to error
                if (firstLoopToErr == -1) {
                    firstLoopToErr = ii;
                }
                loopErrorCount++;
                System.err
                        .println("Error count for the current loop in comparing DoubleBuffer to double src array "
                                + errorCount);
            }


        }// end ii

        System.err.println(loopErrorCount + " loops experienced errors. Total DoubleBuffer loops executed " + NUM_LOOPS);
        if (loopErrorCount > 0) {
            System.err.println("First DoubleBuffer loop to error: " + firstLoopToErr);
        }

    }

    /**
     * Test the IntBuffer
     *
     * @param pSeed   the seed value
     * @param pLength the number of data values
     */
    protected static void testIntBuffer(long pSeed, int pLength) {

        int blength = 4; // 4 btyes
        int[] src = new int[pLength];

        Random r = new Random(pSeed);
//populate src array
        for (int i = 0; i < pLength; i++) {
            src[i] = r.nextInt();
        }


        int loopErrorCount = 0;
        int firstLoopToErr = -1;


        for (int ii = 0; ii <= NUM_LOOPS; ii++) {

            ByteBuffer destbb = ByteBuffer.allocate(pLength * blength);
            IntBuffer fb = destbb.asIntBuffer();
            fb.put(src, 0, pLength);

            int errorCount = 0;
//compare source float values to those copied into the IntBuffer
            for (int i = 0; i < src.length; i++) {
                int fbfvalue = fb.get(i);
                int srcvalue = src[i];
//check for mismatching values
                if (fbfvalue != srcvalue) {
                    errorCount++;

                    if (DUMP_ERROR_VALUES) {
                        System.err.println("*** Data values not equal error");
                        System.err.println(i + " (array index) Expected " + srcvalue
                                + ", got " + fbfvalue);
                    }

                }
            } //end i

//check if there where any errors in loop execution
            if (errorCount > 0) {
//track the first loop to error
                if (firstLoopToErr == -1) {
                    firstLoopToErr = ii;
                }
                loopErrorCount++;
                System.err
                        .println("Error count for the current loop in comparing IntBuffer to double src array "
                                + errorCount);
            }


        }// end ii

        System.err.println(loopErrorCount + " IntBuffer loops experienced errors. Total IntBuffer loops executed " + NUM_LOOPS);
        if (loopErrorCount > 0) {
            System.err.println("First IntBuffer loop to error: " + firstLoopToErr);
        }

    }


}