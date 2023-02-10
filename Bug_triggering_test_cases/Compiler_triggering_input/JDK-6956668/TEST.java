public class TEST
{
    public static int bitTest()
    {
        int result = 0;

        int testValue = 73;
        int bitCount = Integer.bitCount(testValue);

        if (testValue != 0)
        {
            int gap = Long.numberOfTrailingZeros(testValue);
            testValue >>>= gap;

            while (testValue != 0)
            {
                result++;

                if ((testValue ^= 0x1) != 0)
                {
                    gap = Long.numberOfTrailingZeros(testValue);
                    testValue >>>= gap;
                }
            }
        }

        if (bitCount != result)
        {
            System.out.println("ERROR!!");
            System.exit(0);
        }

        return (result);
    }

    public static void main(String[] args)
    {
        for (int i = 0; i < 100000; i++)
        {
            int ct = bitTest();
            System.out.println(">>> " + i + " ... " + ct);
        }
    }
}