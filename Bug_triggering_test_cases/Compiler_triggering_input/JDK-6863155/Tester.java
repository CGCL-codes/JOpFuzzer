public class Tester
{
    private static long test(byte bytes[])
    {
        long value;
        value = bytes[3] << 24 & 0xff000000L;
        value += bytes[2] << 16 & 0xff0000;
        value += bytes[1] << 8 & 0xff00;
        value += bytes[0] & 0xff;
        return(value);
    }

    public static void main(String... args)
            throws Exception
    {
        for (int i = 0; i < 1000; i++)
        {
//byte[] bytes = new byte[] {10, -15, 1, -62};
            byte[] bytes = new byte[] {0x0a, (byte)0xf1, 0x01, (byte)0xc2};
            System.out.println(test(bytes));
        }
    }
}