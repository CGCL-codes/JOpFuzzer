public class test{
private static Unsafe unsafe;

  static {
    try {
      Field field = Unsafe.class.getDeclaredField("theUnsafe");
      field.setAccessible(true);
      unsafe = (Unsafe) field.get(null);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws Exception {
    String first = new String("java");
    String second = "java";
    System.out.println("First object string : " + first);
    System.out.println("Second object string : " + second);
    System.out.println(first);
    System.out.println(second);
    System.out.println("First Hexcode : " + Integer.toHexString(first.hashCode()));
    System.out.println("Second Hexcode : " + Integer.toHexString(second.hashCode()));
    System.out.println("Address of first string : " + addressOf(first));
    System.out.println("Address of second string : " + addressOf(second));
    System.out.print("Bytes of First String : ");
    printBytes(addressOf(first), 4);
    System.out.println();
    System.out.print("Bytes of Second String : ");
    printBytes(addressOf(second), 4);
  }

  public static long addressOf(Object o) throws Exception {
    Object[] array = new Object[] {o};

    long baseOffset = unsafe.arrayBaseOffset(Object[].class);
    int addressSize = unsafe.addressSize();
    long objectAddress;
    switch (addressSize) {
      case 4:
        objectAddress = unsafe.getInt(array, baseOffset);
        break;
      case 8:
        objectAddress = unsafe.getLong(array, baseOffset);
        break;
      default:
        throw new Error("unsupported address size: " + addressSize);
    }

    return (objectAddress);
  }

  public static void printBytes(long objectAddress, int num) {
    for (long i = 0; i < num; i++) {
      int cur = unsafe.getByte(objectAddress + i);
      System.out.print((char) cur);
    }
    System.out.println();
  }}
