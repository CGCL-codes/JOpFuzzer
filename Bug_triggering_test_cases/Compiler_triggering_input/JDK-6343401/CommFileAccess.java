class CommFileAccess
{
    byte[] cmp_wk = new byte[1024];

    public static void main(String[] args)
    {
        CommFileAccess x = new CommFileAccess();
        byte[] ba = new byte[1];
        x.compressMsg(ba, 0);
    }

    int compressMsg(byte[] ba, int iarg2)
    {
        int i_4;
        int i_5 = 0;
        int i_6 = 0;
        int i_8 = 0;
        byte[] ba_11 = new byte[64];
        int i_13 = 0;
        byte[] ba_14 = new byte[2];

        for (;i_5 < iarg2; i_5++)
        {
            if (10 == ba[iarg2 + i_5])
            {
                if (i_8 > 0)
                {
                    memoCopy(cmp_wk, i_13, ba_11, 0, i_8);
                }
                continue;
            }

            for (i_4 = 0; i_4 < (i_6+1); i_4++)
            {
                i_8++;
                memoCopy(cmp_wk, i_13, ba_11, 0, i_8);
                i_13 += i_8;
            }
        }

        memoCopy(cmp_wk, i_13, ba_14, 0, 1);
        i_13++;
        for (i_4 = 0; i_4 < i_13; i_4++) {}
        return i_13;
    }

    static void memoCopy(byte[] ta, int ti, byte[] sa, int si, int len) {
        for (int i = 0; i < len; i++) {
            ta[ti+i] = sa[si+i];
        }
    }
}