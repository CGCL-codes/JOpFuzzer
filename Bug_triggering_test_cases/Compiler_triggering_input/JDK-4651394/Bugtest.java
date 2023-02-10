public class Bugtest
{
    private int modulus;
    private long imodulus;

    public void setModulus(int m)
    {
        modulus = m;
        imodulus = (long) (9223372036854775808.0 / (double) m);
    }

    public int modmul(int a, int b)
    {
        long t;
        int r;

        t = (long) a * (long) b;
        r = (int) t - (int) ((t >>> 30) * imodulus >>> 33) * modulus;

        r = (r >= modulus || r < 0 ? r - modulus : r);

        return r;
    }

    public static void main(String[] args)
    {
        Bugtest modmul = new Bugtest();
        modmul.setModulus(2146435073);
        int s = 0;

        for (int i = 0; i < 10000000; i++)
            s += modmul.modmul(i, i);

        System.out.println(s);
    }
}