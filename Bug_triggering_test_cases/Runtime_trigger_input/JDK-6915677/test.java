
/*
 * @test
 * @bug 6244515
 * @summary Unsafe operations that access invalid memory are handled gracefully
 *
 *   This test passes if it doesn't crash.  It should throw an exception.
 */
import java.io.*;
import java.nio.*;
import java.nio.channels.*;


public class Truncate {

    private static final int SIZE = 1 << 16;

    public static void main(String[] args) throws Exception {
      // This test case will not pass on Windows since Windows does not
      // support truncating file while it is still having user-mapped region
      // open. So we just return on Windows.
      if (System.getProperty("os.name").toLowerCase().startsWith("windows")) {
        System.out.println("Test Passed. Note that due to some platform dependent behaviours, this
test is not actually executed on Windows platform");
        return;
      }
      File tmpfile = File.createTempFile("zzz", null);
      try {
        FileChannel fc = new RandomAccessFile(tmpfile, "rw").getChannel();
        fc.position(SIZE);
        fc.write(ByteBuffer.allocate(1));
        MappedByteBuffer bb
            // Uncomment this line for 1.4.0 beta2
            //= fc.map(FileChannel.MAP_RW, 0, SIZE);
            // Uncomment this line for 1.4.0 beta3 or later
            = fc.map(FileChannel.MapMode.READ_WRITE, 0, SIZE);

        bb.put((byte)1).put((byte)2).put((byte)3).put((byte)4);
        fc.truncate(0);
        bb.put((byte)5);
        System.out.println("bb.put above got a bus error");
      } catch (InternalError e) {
        System.out.println("Test Passed");
      } finally {
        if (tmpfile != null) {
          tmpfile.delete();
        }
      }
    }

}

