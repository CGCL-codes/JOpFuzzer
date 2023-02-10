class t {
    static char buf[];

    static int tradAlphaCount(long val)
    {
        if (val > Long.MAX_VALUE) {
            return 0;
        }

        buf = new char[100];
        return 1;
    }

    public static void main(String argv[]) {
        tradAlphaCount(100L);
    }
}
