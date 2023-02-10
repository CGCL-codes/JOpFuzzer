class eclipsetest {
    static final byte[] byteBuf = new byte[10000];
    static final char[] buf = new char[10000];

    public static void main( String[] args ) throws Exception {
        for ( int i = 0; i < 1000; i++ ) {
            redo_work(1000, 100, 100);
        }
        System.out.println("Warmup done.");

        for ( int x = 0; x < 4; x++ ) {
            long start = System.currentTimeMillis();

            redo_work(1000, 100, 100000);

            long end = System.currentTimeMillis();
            System.out.println( "total time: " + (end - start) );
        }
    }

    public static int redo_work(int byteIndex, int offset, int redo) {
        for ( int i = 0; i < redo; i++ ) {
            work(byteIndex, offset);
        }
        return 0;
    }

    public static int work(int byteIndex, int offset) {
        if ( (byteIndex - offset) < 0 )
            throw new RuntimeException();

        for ( ; byteIndex < byteBuf.length; byteIndex++ ) {
            if ( byteBuf[byteIndex] < 6 ) {
                if ( byteBuf[byteIndex] == 5 ) {
                    buf[byteIndex - offset] = '\n';
                }
            }
            buf[byteIndex - offset] = (char)byteBuf[byteIndex];
        }

        return 0;
    }
}