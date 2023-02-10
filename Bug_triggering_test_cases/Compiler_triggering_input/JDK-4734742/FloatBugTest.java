import java.util.Random;

public class FloatBugTest
{
    private float modulus;
    private double inverseModulus;

    public final void setModulus(float modulus)
    {
        this.inverseModulus = 1.0 / (double) modulus;
        this.modulus = modulus;
    }

    public float modMultiply(float a, float b)
    {
        double r = (double) a * (double) b;

        return (float) (r - (double) this.modulus * (double) (int)
                (this.inverseModulus * r));
    }

    public float modInverse(float a)
    {
        return modPow(a, this.modulus - 2);
    }

    public float modPow(float a, float n)
    {
        long exponent = (long) n;

        while ((exponent & 1) == 0)
        {
            a = modMultiply(a, a);
            exponent >>= 1;
        }

        float r = a;

        while ((exponent >>= 1) > 0)
        {
            a = modMultiply(a, a);
            if ((exponent & 1) != 0)
            {
                r = modMultiply(r, a);
            }
        }

        return r;
    }

    public static void main(String[] args)
    {
        FloatBugTest math = new FloatBugTest();
        math.setModulus(16515073.0f);
        Random random = new Random(1234);
        float s1 = 0, s2 = 0;

        for (int i = 0; i < 1000; i++)
        {
            float x = (float) (Math.abs(random.nextLong()) % 16515073);

            s1 += math.modMultiply(x, x); // Works
            s2 += math.modMultiply(math.modInverse(x), x); // Buggy
        }

        System.out.println(s1);
        System.out.println(s2);
    }
}