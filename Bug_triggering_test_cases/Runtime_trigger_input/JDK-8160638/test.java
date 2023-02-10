
import java.nio.*;
import java.util.*;

public class Buffers {
  // 32MB * 256 == 8GB
  private static final int BUFFER_SIZE = 32 * 1024 * 1024;
  private static final int BUFFER_COUNT = 256;

  public static void main(String[] args) throws Exception {
    long counter = 0L;
    try {
        List<ByteBuffer> buffers = new ArrayList<ByteBuffer>();
        for (int i=0 ; i<BUFFER_COUNT ; i++) {
                ByteBuffer b = ByteBuffer.allocateDirect(BUFFER_SIZE);
                buffers.add(b);
                counter += BUFFER_SIZE;
        }
    } catch(Throwable t) {
        t.printStackTrace();
    }
    System.out.println("allocated : " + (counter / 1024 / 1024 + " MB"));
  }

}
