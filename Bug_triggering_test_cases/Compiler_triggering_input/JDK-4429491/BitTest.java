class BitTest {
    private static boolean isDebug = true;
    private int total = 0;
    private int errorCount = 0;
    private byte buf[] = new byte[500];
    private int ofsStartPut = 0;
    private int blen = 0;
 
    int bitSwizzle(int b1, int b2) {
         int b3 = (b1 >> 4) | ((b2 & 0x3) << 4);
         return b3;
    }
 
 
    private final void encode(byte[] inBuf, int inStart, int inEnd)
    {
       ofsStartPut = 0;
       int iTimes = -1;
       int b1;
       int b2 = 0;
       int b2Correct;
       blen += (inEnd - inStart);
       while (inStart < inEnd) {
          b1 = inBuf[inStart++] & 0xFF;
          switch (++iTimes) {
          case 0:
             b2 = b1>>2;
             break;
          case 1:
             b2Correct = bitSwizzle(b1, b2);
             b2 = (b1>>4) | ((b2 & 0x3)<<4);
             if (isDebug) {
                ++total;
                if (b2 != b2Correct) {
                    ++errorCount;
                    System.err.println(
                       "Error: Expecting: 0x" + Integer.toHexString(b2Correct) +
                       " received: 0x" + Integer.toHexString(b2)
                    );
                }
             }
             break;
          default: // case 2:
             buf[ofsStartPut++] = (byte)((b1>>6) | ((b2 & 0xF)<<2));
             b2 = b1 & 0x3f;
             iTimes = -1;
             break;
          }
          buf[ofsStartPut++] = (byte)b2;
          b2 = b1;
       }
 
       switch (iTimes) { // take care of orphans!
       case 0:
          buf[ofsStartPut++] = (byte)((b2 & 0x3)<<4);
          break;
       case 1:
          buf[ofsStartPut++] = (byte)((b2 & 0xF)<<2);
          break;
       default: // case 2 (really: -1)
          break;
       }
    }
 
     public static void main(String[] args) {
         int errorCount = 0;
         BitTest my = new BitTest();
 
         for (int k=0; k < 50; ++k) {
           /* ----+----1----+----2----+----3----+----4----- */
            my.encode(
               "Hello World.\r\nHello World.\r\nAbcdefg Abcdefg A".getBytes(),
               0,
               45
            );
         }
         System.out.println(
            "Total errors this run: " + my.errorCount +
            " for a total of " + my.total + " bit swizzlings"
         );
     }
 }