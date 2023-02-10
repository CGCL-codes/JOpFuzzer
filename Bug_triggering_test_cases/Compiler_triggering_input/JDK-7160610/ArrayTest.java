public class ArrayTest {
    private static final byte[] $BYTE_ARRAY = new byte[7];
    private static int[] anIntArray1190 = new int[32768];
    private static int[] anIntArray1191 = new int[32768];

    public static void main(String args[])
    {
        int i = 256;
        for(int j = $BYTE_ARRAY[2]; j < anIntArray1190.length; j++)
        {
            anIntArray1190[j] = $BYTE_ARRAY[2];
        }

        for(int k = $BYTE_ARRAY[2]; (k ^ $BYTE_ARRAY[1]) > -5001; k++)
        {
            int i1 = (int)(Math.random() * 128D * (double)i);
            anIntArray1190[i1] = (int)(Math.random() * 256D);
        }

        System.out.println(3);
        for(int l = $BYTE_ARRAY[2]; (l ^ $BYTE_ARRAY[1]) > -21; l++)
        {
            for(int j1 = $BYTE_ARRAY[0]; j1 < i + -$BYTE_ARRAY[0]; j1++)
            {
                for(int k1 = $BYTE_ARRAY[0]; (k1 ^ $BYTE_ARRAY[1]) > -128; k1++)
                {
                    int l1 = k1 - -(j1 << 0x26cb6487);
                    anIntArray1191[l1] = (anIntArray1190[l1 + -$BYTE_ARRAY[0]] - -anIntArray1190[l1 - -$BYTE_ARRAY[0]] - -anIntArray1190[-128 + l1] - -anIntArray1190[128 + l1]) / $BYTE_ARRAY[6];
                }

            }

            int ai[] = anIntArray1190;
            anIntArray1190 = anIntArray1191;
            anIntArray1191 = ai;
        }

    }

    static {
        $BYTE_ARRAY[6] = 4;
        $BYTE_ARRAY[5] = 5;
        $BYTE_ARRAY[4] = 3;
        $BYTE_ARRAY[3] = 2;
        $BYTE_ARRAY[2] = 0;
        $BYTE_ARRAY[1] = -1;
        $BYTE_ARRAY[0] = 1;
    }
}
