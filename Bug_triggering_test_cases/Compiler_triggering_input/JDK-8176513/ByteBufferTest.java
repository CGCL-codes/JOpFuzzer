import java.nio.*;

public class ByteBufferTest {

    static final int SIZE = 1024;

    static final ByteBuffer buf = ByteBuffer.allocate(SIZE * 4);
    IntBuffer ibuf;

    ByteBufferTest() {
        buf.order(ByteOrder.LITTLE_ENDIAN);
        ibuf = buf.asIntBuffer();
    }

    static private final ByteBufferTest test = new ByteBufferTest();

    void floss(ByteBuffer b, int n) {
        for (int i = 0; i < b.capacity(); i++) {
            buf.putInt(i<<2, n);
        }
    }

    void floss2(IntBuffer b, int n) {
        for (int i = 0; i < b.capacity(); i++) {
            b.put(i, n);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10000000; i++)
            test.floss(test.buf, i);
    }
}
