
package jvmcrash;
public class Crash {

    /** Bytecodes for the class
     * <pre>
     *      package jvmcrash;
     *      public class TestClass {
     *          private static int seqno;
     *          private final int idno = ++seqno;
     *          public TestClass() { }
     *          @Override
     *          public String toString() {
     *              return "[TestClass " + idno + "]";
     *          }
     *      }
     * </pre>
     */
    private static final byte[] classBytes = { -54, -2, -70, -66, 0, 0, 0, 50,
        0, 41, 10, 0, 12, 0, 27, 9, 0, 11, 0, 28, 9, 0, 11, 0, 29, 7, 0, 30, 10,
        0, 4, 0, 27, 8, 0, 31, 10, 0, 4, 0, 32, 10, 0, 4, 0, 33, 8, 0, 34, 10,
        0, 4, 0, 35, 7, 0, 36, 7, 0, 37, 1, 0, 5, 115, 101, 113, 110, 111, 1, 0,
        1, 73, 1, 0, 4, 105, 100, 110, 111, 1, 0, 6, 60, 105, 110, 105, 116, 62,
        1, 0, 3, 40, 41, 86, 1, 0, 4, 67, 111, 100, 101, 1, 0, 15, 76, 105, 110,
        101, 78, 117, 109, 98, 101, 114, 84, 97, 98, 108, 101, 1, 0, 18, 76,
        111, 99, 97, 108, 86, 97, 114, 105, 97, 98, 108, 101, 84, 97, 98, 108,
        101, 1, 0, 4, 116, 104, 105, 115, 1, 0, 20, 76, 106, 118, 109, 99, 114,
        97, 115, 104, 47, 84, 101, 115, 116, 67, 108, 97, 115, 115, 59, 1, 0, 8,
        116, 111, 83, 116, 114, 105, 110, 103, 1, 0, 20, 40, 41, 76, 106, 97,
        118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 1,
        0, 10, 83, 111, 117, 114, 99, 101, 70, 105, 108, 101, 1, 0, 14, 84, 101,
        115, 116, 67, 108, 97, 115, 115, 46, 106, 97, 118, 97, 12, 0, 16, 0, 17,
        12, 0, 13, 0, 14, 12, 0, 15, 0, 14, 1, 0, 23, 106, 97, 118, 97, 47, 108,
        97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 66, 117, 105, 108, 100,
        101, 114, 1, 0, 11, 91, 84, 101, 115, 116, 67, 108, 97, 115, 115, 32,
        12, 0, 38, 0, 39, 12, 0, 38, 0, 40, 1, 0, 1, 93, 12, 0, 23, 0, 24, 1, 0,
        18, 106, 118, 109, 99, 114, 97, 115, 104, 47, 84, 101, 115, 116, 67,
        108, 97, 115, 115, 1, 0, 16, 106, 97, 118, 97, 47, 108, 97, 110, 103,
        47, 79, 98, 106, 101, 99, 116, 1, 0, 6, 97, 112, 112, 101, 110, 100, 1,
        0, 45, 40, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116,
        114, 105, 110, 103, 59, 41, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103,
        47, 83, 116, 114, 105, 110, 103, 66, 117, 105, 108, 100, 101, 114, 59,
        1, 0, 28, 40, 73, 41, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47,
        83, 116, 114, 105, 110, 103, 66, 117, 105, 108, 100, 101, 114, 59, 0,
        33, 0, 11, 0, 12, 0, 0, 0, 2, 0, 10, 0, 13, 0, 14, 0, 0, 0, 18, 0, 15,
        0, 14, 0, 0, 0, 2, 0, 1, 0, 16, 0, 17, 0, 1, 0, 18, 0, 0, 0, 68, 0, 3,
        0, 1, 0, 0, 0, 18, 42, -73, 0, 1, 42, -78, 0, 2, 4, 96, 89, -77, 0, 2,
        -75, 0, 3, -79, 0, 0, 0, 2, 0, 19, 0, 0, 0, 14, 0, 3, 0, 0, 0, 7, 0, 4,
        0, 6, 0, 17, 0, 7, 0, 20, 0, 0, 0, 12, 0, 1, 0, 0, 0, 18, 0, 21, 0, 22,
        0, 0, 0, 1, 0, 23, 0, 24, 0, 1, 0, 18, 0, 0, 0, 70, 0, 2, 0, 1, 0, 0, 0,
        28, -69, 0, 4, 89, -73, 0, 5, 18, 6, -74, 0, 7, 42, -76, 0, 3, -74, 0,
        8, 18, 9, -74, 0, 7, -74, 0, 10, -80, 0, 0, 0, 2, 0, 19, 0, 0, 0, 6, 0,
        1, 0, 0, 0, 10, 0, 20, 0, 0, 0, 12, 0, 1, 0, 0, 0, 28, 0, 21, 0, 22, 0,
        0, 0, 1, 0, 25, 0, 0, 0, 2, 0, 26, };


    public static void main(String[] unused) {
        System.err.println("trying unmodified bytecodes ...");
        if (! new Loader().loadAndGo(classBytes))
            System.exit(1);

//        for (int bitno = classBytes.length * 8;  --bitno >= 0;  ) {
//        for (int bitno = 3914;  --bitno >= 0;  ) {
        for (int bitno = 100;  --bitno >= 0;  ) {
            System.err.println("flipping bit " + bitno + " ...");
            classBytes[bitno >> 3] ^= 1 << (bitno & 7);
            new Loader().loadAndGo(classBytes);
            classBytes[bitno >> 3] ^= 1 << (bitno & 7);
        }
    }
    

    private static class Loader extends ClassLoader {
        /** Tries to load and instantiate a class defined by an array of bytecodes.
         *  Catches and reports any exception that is thrown in consequence.
         * @param classFile The bytecodes, in class file format.
         * @return true if successful, false if an exception is thrown.
         */
        private boolean loadAndGo(byte[] classFile) {
            try {
                Class clown = defineClass(null, classFile, 0, classFile.length);
                resolveClass(clown);
                Object obj = clown.newInstance();
                System.err.println("\t" + obj);
                return true;
            }
            catch (Throwable thrown) {
                System.err.println("\t" + thrown);
                return false;
            }
        }
    }
}

