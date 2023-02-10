import java.io.IOException;


public class TestMe {
    public static final byte[] BINARY_VERSION_MARKER_1_0 = { (byte) 0xE0,
            (byte) 0x01,
            (byte) 0x00,
            (byte) 0xEA };

    public static final int BINARY_VERSION_MARKER_SIZE =
            BINARY_VERSION_MARKER_1_0.length;

    public static void main(String[] args) throws Exception {
        TestMe kase = new TestMe();
        kase.test();
    }

    public void test() throws Exception {
        byte[] x = new byte[5];
        for (int i=0; i < x.length && i<BINARY_VERSION_MARKER_SIZE; ++i) {
            x[i] = BINARY_VERSION_MARKER_1_0[i];
        }
        for (int i=BINARY_VERSION_MARKER_SIZE; i<x.length; ++i) {
            x[i] = 9;
        }
 
        for (int i=0; i<3_000_000; ++i) {
            has_binary_cookie(x);
        }
    }

    static void unread(byte[] args) {
	
    }

    static final boolean has_binary_cookie(byte[] uis) throws IOException {
        byte[] bytes = new byte[BINARY_VERSION_MARKER_SIZE];

        //System.out.println("has_binary_cookie has been invoked!!!");

        // try to read the first 4 bytes and unread them (we want
        // the data stream undisturbed by our peeking ahead)
        int len;
        for (len = 0; len < BINARY_VERSION_MARKER_SIZE; len++) {
            int c = len < uis.length ? uis[len] : -1;
            if (c == -1) {
                break;
            }
            bytes[len] = (byte)c;
        }

        for (int ii=len; ii>0; ) {
	    unread(bytes);
            ii--;
        }

        int offset = 0;
        if (bytes == null ||  len < BINARY_VERSION_MARKER_SIZE) {
            return false;
        }
/*
        for (len = 0; len < BINARY_VERSION_MARKER_SIZE; len++) {
            if (BINARY_VERSION_MARKER_1_0[len] != bytes[offset + len]) {
                return false;
            }
        }
*/
        return true;
    }
}

