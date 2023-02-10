import java.nio.*;

class Endian {
  public static void main(String[] args) {
    ByteBuffer bb = ByteBuffer.allocate(100);
    ByteOrder bo = bb.order();
    System.out.println(bo.toString());
  }
}