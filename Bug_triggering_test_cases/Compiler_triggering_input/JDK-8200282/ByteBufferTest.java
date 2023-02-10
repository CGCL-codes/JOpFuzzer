import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Random;

/**
 * Test to illustrate likely JIT bug in which
 * the fourth byte of an IP address, which is known
 * to be non-zero, is serialized as zero.
 */
public class ByteBufferTest
{
    private static String[] addresses = {"239.193.225.53",
            "239.193.225.54",
            "239.193.225.55",
            "239.193.225.56"};

    public static void main(String[] args)
    {
        ByteBuffer buf = ByteBuffer.allocate(256);

        for (int i = 1; i < 100000; i++) {
            buf.position(0);
            buf.limit(42);

            write(buf, i);

            // Read the full buffer content into a byte[] and
            // log if 0 byte was detected at location of last octet.
            buf.position(0);
            byte[] fullBuf = new byte[buf.remaining()];
            buf.get(fullBuf);

            if (fullBuf[33] == 0) {
                System.out.println("main(): Full Buffer contents when issue is detected: "
                        + Arrays.toString(fullBuf));
            }
            buf.clear();
        }
    }

    private static void write(ByteBuffer buf, int itrCount)
    {
        Random r = new Random();

        // put some ints
        for (int j = 0; j < 5; j++) {
            buf.putInt(r.nextInt());
        }
        buf.putLong(r.nextLong());
        buf.putChar('m');

        // Pick a multicast address from "addresses" at random
// and serialize it to the buffer. Note that we know
// by contruction that the last octet of the address
// is non-zero.
        byte[] mcastaddr = new InetSocketAddress(addresses[r.nextInt(4)], 9020)
                .getAddress().getAddress();
        buf.put(mcastaddr);

        // Read back the multicast address bytes that were just written
        // into the buffer.
        buf.position(buf.position() - 4);
        byte[] readAddrBytes = new byte[4];
        buf.get(readAddrBytes);

        // Check if there was a 0 in the last octet of multicast address.
        if (readAddrBytes[3] == 0) {

            // For some reason, this issue does not seem to happen often if
            // we directly log mcastaddr original address bytes.
            // So we copy this into a new temp array for logging.
            byte[] addrBytesCopy = new byte[4];
            System.arraycopy(mcastaddr, 0, addrBytesCopy, 0, 4);

            System.out.println("write(): Detected 0 byte in last octet after "
                    + itrCount + " th iteration. Original addr bytes written were: "
                    + Arrays.toString(addrBytesCopy)
                    + "; bytes read from buffer are: " + Arrays.toString(readAddrBytes)
            );

        }
        buf.putLong(r.nextLong());
    }

}