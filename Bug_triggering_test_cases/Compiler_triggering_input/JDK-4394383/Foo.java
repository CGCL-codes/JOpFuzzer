public class Foo
{
  protected static void encodeInteger(long value, byte[] coding, int index)
  {
    int end = index;
    long v = value;

    do {
      coding[--index] = (byte)v;
      v >>= 8;
    } while (v != 0 && v != -1); // Until just sign

    // Check if the sign of the end byte and value are different
    if (v == 0 && coding[index] < 0 || v == -1 && coding[index] >= 0){
      //System.out.println("Entered for sign extension");
      coding[--index] = (byte)v; // Sign resolver byte
    }

    int length = (end - index - 1);
    int testIndex = index;
    coding[--index] = (byte)(end - index - 1); // Length: 1-4
    //index = encodeTag(object.getAsnType(), coding, index);

    // reconstitute the value and test to be sure it's what we expected
    long origV = value;
    long testV = ((int)coding[testIndex] >> 8); // Sign extend
    while (length-- >= 0) {
      testV = (testV << 8) | (coding[testIndex++] & 0xff);
    }
    if (testV != origV)
      System.out.println("ERROR: Encoded int value " + origV + " decoded as " + testV);
  }

  public static void main(String[] args)
  {
    byte[] coding = new byte[10];
    //for (long value = 0; value < 0xfffffff; value++) {
    for (long value = 0; value < 140; value++) {
      encodeInteger(value, coding, coding.length);
    }
  }
}