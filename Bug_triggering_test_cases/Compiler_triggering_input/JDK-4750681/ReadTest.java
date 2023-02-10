public class ReadTest {

    private Buffer _current;
    private final int BYTE_SIZE = 1;
    private int _offsetInChunk;

    public static void main(String[] args) {
        final String _data = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        Buffer in = new Buffer(_data);
        ReadTest test = new ReadTest(in);
        char[] value = new char[10];
        int offset = 0;
        int length = 10;
        while (true) {
            for(int i=0; i<6; i++) {
                test.testMethod(value, offset, length);
                System.out.println(value);
            }
            Buffer in2 = new Buffer(_data);
            in.next = in2;
            in = in2;
        }
    }

    private ReadTest(Buffer buffer) {
        _current = buffer;
    }

    private void testMethod(char[] value, int offset, int length) {

        if((value == null) || (value.length-offset < length)) {
            System.out.println("### ERROR1 ###");
            return;
        }

        int inLengthInBytes = length * BYTE_SIZE;
        int numBytesToCopy = 0;

        while(inLengthInBytes > 0) {
            if (_offsetInChunk >= _current.dataEndOffset) {
                _offsetInChunk -= _current.dataEndOffset;
                if(_current.next == null) {
                    System.out.println("### ERROR2 ###");
                    return;
                }
                _current = _current.next;
                _offsetInChunk += _current.dataStartOffset;
            }

            numBytesToCopy = _current.dataEndOffset - _offsetInChunk;
            if (inLengthInBytes < numBytesToCopy) {
                numBytesToCopy = inLengthInBytes;
            }

            int i = 0, max = 0;
            try {
                for(i = offset, max = numBytesToCopy; max > 0; i++, max -= BYTE_SIZE) {
                    value[i] = (char)(_current.data[_offsetInChunk++] & 0xff);
                    if (value[i] == 0) {
                        System.out.println("### ERROR3 ###");
                        return;
                    }
                }
            } catch(ArrayIndexOutOfBoundsException aioobe) {
                System.out.println("+++++++++++++++++++++++++++++");
                System.out.println(" value = " + new String(value));
                System.out.println(" value's length = " + value.length);
                System.out.println(" value's i = " + i);
                System.out.println(" max = " + max);
                System.out.println(" numBytesToCopy = " + numBytesToCopy);
                System.out.println(" BYTE_SIZE = " + BYTE_SIZE);
                System.out.println(" offset = " + offset);
                System.out.println("+++++++++++++++++++++++++++++");
                System.exit(1);
            }
            inLengthInBytes -= numBytesToCopy;
            offset += numBytesToCopy / BYTE_SIZE;
        }
    }

}

class Buffer {

    public byte[] data;
    public int dataStartOffset;
    public int dataEndOffset;
    public Buffer next;

    public Buffer(String _data) {
        int chunkSize = dataEndOffset = _data.length();
        data = _data.getBytes();
    }
}
