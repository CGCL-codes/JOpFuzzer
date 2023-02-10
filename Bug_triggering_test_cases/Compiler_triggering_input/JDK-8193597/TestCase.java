
import java.util.*;
import java.nio.*;
import java.nio.charset.*;

public class TestCase {

    public static void main(String[] args) throws Throwable {

        // full bmp first
        char[] bmp = new char[0x10000];
        for (int i = 0; i < 0x10000; i++) {
            bmp[i] = (char)i;
        }

        Charset cs = Charset.forName("GBK");

System.out.printf("Testing" + cs.name());

        // "randomed" sizes
        Random rnd = new Random();
        for (int i = 0; i < 10000; i++) {
            //System.out.printf("    blen=%d, clen=%d%n", blen, clen);
            char[] bmp0 = Arrays.copyOf(bmp, rnd.nextInt(0x10000));

	    if (bmp0.length == 0)
                continue;

            //add a pair of surrogates
            int pos = bmp0.length / 2;

            if ((pos + 1) < bmp0.length) {
                bmp0[pos] = '\uD800';
                bmp0[pos+1] = '\uDC00';
            }

System.out.println("---------------------------------------------");
System.out.printf("testGetBytes.[i=%d].len=%d, supp.at.pos=%x%n", i, bmp0.length, pos);

if (pos-2 >=0)
System.out.printf("[%x]: %x%n", pos-2, bmp0[pos-2] & 0xffff);
if (pos-1 >=0)
System.out.printf("[%x]: %x%n", pos-1, bmp0[pos-1] & 0xffff);

System.out.printf("[%x]: %x%n", pos,   bmp0[pos] & 0xffff);

if (pos+1 < bmp0.length)
System.out.printf("[%x]: %x%n", pos+1, bmp0[pos+1] & 0xffff);
if (pos+2 < bmp0.length)
System.out.printf("[%x]: %x%n", pos+2, bmp0[pos+2] & 0xffff);


            testGetBytes(cs, new String(bmp0));

        }
        System.out.println("done!");
    }

    static byte[] getBytes(CharsetEncoder enc, String str) throws Throwable {
        ByteBuffer bf = enc.reset().encode(CharBuffer.wrap(str.toCharArray()));
        byte[] ba = new byte[bf.limit()];
        bf.get(ba, 0, ba.length);
        return ba;
    }

    static void testGetBytes(Charset cs, String str) throws Throwable {
        CharsetEncoder enc = cs.newEncoder()
            .onMalformedInput(CodingErrorAction.REPLACE)
            .onUnmappableCharacter(CodingErrorAction.REPLACE);

        byte[] baNIO = getBytes(enc, str);
        byte[] baSC = str.getBytes(cs.name());

        if (!Arrays.equals(baSC, baNIO)) {

System.out.printf("baSc.len=%d, baNIO.len=%d%n", baSC.length, baNIO.length);
for (int i = 0; i < Math.min(baSC.length, baNIO.length); i++) {
    if (baSC[i] != baNIO[i]) {

        if (i-2 >=0)
        System.out.printf("[%x]: %x    vs   %x%n", i-2, baSC[i-2] & 0xff, baNIO[i-2] & 0xff);  
        if (i-1 >=0)
        System.out.printf("[%x]: %x    vs   %x%n", i-1, baSC[i-1] & 0xff, baNIO[i-1] & 0xff);  
	System.out.printf("[%x]: %x    vs   %x%n", i, baSC[i] & 0xff, baNIO[i] & 0xff);  
        if (i+1 < baSC.length)
        System.out.printf("[%x]: %x    vs   %x%n", i+1, baSC[i+1] & 0xff, baNIO[i + 1] & 0xff);
        if (i+2 < baSC.length)
	System.out.printf("[%x]: %x    vs   %x%n", i+2, baSC[i+2] & 0xff, baNIO[i+2] & 0xff);    
	break;
    }
}
            throw new RuntimeException("getBytes(csn) failed  -> " + cs.name());
        }

    }

}
