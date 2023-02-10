
public class TestJniOom {

  public static final int TEST_NOF_ELEMENTS = 1024 * 1024; 

  public static String    TEST_STRING   = createTestString(TEST_NOF_ELEMENTS);
  public static boolean[] TEST_BOOLEANS = createTestBooleans(TEST_NOF_ELEMENTS);

  public static String createTestString(int nofChars) {
    char[] chars = new char[nofChars];
    for (int i = 0; i < nofChars; i++) {
      chars[i] = (char) ('a' + (i % 26));
    }
    return new String(chars);
  }

  public static boolean[] createTestBooleans(int nofElements) {
    boolean[] booleans = new boolean[nofElements];
    for (int i = 0; i < nofElements; i++) {
      booleans[i] = (i % 1 == 0);
    }
    return booleans;
  }

  static {
    System.loadLibrary("TestJniOom");
  }

  static native void testJniOomGetStringChars(String testString);
  static native void testJniOomGetStringUTFChars(String testString);

  static native void testJniOomGetBooleanArrayElements(boolean[] array);

  public static void main(String[] args) {
     
    testJniOomGetStringChars(TEST_STRING);
    testJniOomGetStringUTFChars(TEST_STRING);
    testJniOomGetBooleanArrayElements(TEST_BOOLEANS);
  }

}