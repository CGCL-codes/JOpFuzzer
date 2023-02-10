import java.util.Random;

class FloatModMath
{
    public final float modMultiply(float a, float b)
    {
        double r = (double) a * (double) b;

        return (float) (r - (double) this.modulus * (double) (int) (this.inverseModulus * r));
    }

    public final float modAdd(float a, float b)
    {
        double r = (double) a + (double) b;

        return (float) (r >= (double) this.modulus ? r - (double) this.modulus : r);
    }

    public final float modSubtract(float a, float b)
    {
        float r = a - b;

        return (r < 0.0f ? r + this.modulus : r);
    }

    public float getForwardNthRoot(float primitiveRoot, long n)
    {
        return modPow(primitiveRoot, getModulus() - 1 - (getModulus() - 1) / (float) n);
    }

    public float getInverseNthRoot(float primitiveRoot, long n)
    {
        return modPow(primitiveRoot, (getModulus() - 1) / (float) n);
    }

    public final float modInverse(float a)
    {
        return modPow(a, getModulus() - 2);
    }

    public final float modDivide(float a, float b)
    {
        return modMultiply(a, modInverse(b));
    }

    public final float modPow(float a, float n)
    {
        assert (a != 0 || n != 0);

        if (n == 0)
        {
            return 1;
        }
        else if (n < 0)
        {
            return modPow(a, getModulus() - 1 + n);
        }

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

    public final float getModulus()
    {
        return this.modulus;
    }

    public final void setModulus(float modulus)
    {
        this.inverseModulus = 1.0 / (double) modulus;
        this.modulus = modulus;
    }

    private float modulus;
    private double inverseModulus;
}

public class FloatModMathTest
{
    public static final float MODULUS[] = { 16515073.0f, 14942209.0f, 14155777.0f };

    public static final float PRIMITIVE_ROOT[] = { 5.0f, 11.0f, 7.0f };

    public static void main(String[] args)
    {
        testGetForwardNthRoot();
        testGetInverseNthRoot();
        testInverse();
        testDivide();
        testPow();
    }

    public static void testGetForwardNthRoot()
    {
        FloatModMath math = new FloatModMath();
        math.setModulus(MODULUS[0]);
        float w = math.getForwardNthRoot(PRIMITIVE_ROOT[0], 4);

        assertEquals("w^(n/2)", (long) MODULUS[0] - 1, (long) math.modMultiply(w, w));
        assertEquals("w^n", 1, (long) math.modMultiply(w, math.modMultiply(w, math.modMultiply(w, w))));
    }

    public static void testGetInverseNthRoot()
    {
        FloatModMath math = new FloatModMath();
        math.setModulus(MODULUS[0]);
        float w = math.getInverseNthRoot(PRIMITIVE_ROOT[0], 4);

        assertEquals("w^(n/2)", (long) MODULUS[0] - 1, (long) math.modMultiply(w, w));
        assertEquals("w^n", 1, (long) math.modMultiply(w, math.modMultiply(w, math.modMultiply(w, w))));
    }

    public static void testInverse()
    {
        FloatModMath math = new FloatModMath();
        Random random = new Random();

        for (int modulus = 0; modulus < 3; modulus++)
        {
            math.setModulus(MODULUS[modulus]);
            long lm = (long) MODULUS[modulus],
                    x;

            x = 1;
            assertEquals(x + " ^ -1 % " + lm, 1L, (long) math.modMultiply(math.modInverse((float) x), (float) x));

            x = lm - 1;
            assertEquals(x + " ^ -1 % " + lm, 1L, (long) math.modMultiply(math.modInverse((float) x), (float) x));

            for (int i = 0; i < 1000; i++)
            {
                x = Math.abs(random.nextLong()) % lm;

                assertEquals(x + " ^ -1 % " + lm, 1L, (long) math.modMultiply(math.modInverse((float) x), (float) x));
            }
        }
    }

    public static void testDivide()
    {
        FloatModMath math = new FloatModMath();
        Random random = new Random();

        for (int modulus = 0; modulus < 3; modulus++)
        {
            math.setModulus(MODULUS[modulus]);
            long lm = (long) MODULUS[modulus],
                    x, y;

            x = 0;
            y = 1;
            assertEquals(x + " / " + y + " % " + lm, x, (long) math.modMultiply(math.modDivide((float) x, (float) y), (float) y));

            x = 0;
            y = lm - 1;
            assertEquals(x + " / " + y + " % " + lm, x, (long) math.modMultiply(math.modDivide((float) x, (float) y), (float) y));

            x = 1;
            y = 1;
            assertEquals(x + " / " + y + " % " + lm, x, (long) math.modMultiply(math.modDivide((float) x, (float) y), (float) y));

            x = lm - 1;
            y = lm - 1;
            assertEquals(x + " / " + y + " % " + lm, x, (long) math.modMultiply(math.modDivide((float) x, (float) y), (float) y));

            x = 1;
            y = lm - 1;
            assertEquals(x + " / " + y + " % " + lm, x, (long) math.modMultiply(math.modDivide((float) x, (float) y), (float) y));

            x = lm - 1;
            y = 1;
            assertEquals(x + " / " + y + " % " + lm, x, (long) math.modMultiply(math.modDivide((float) x, (float) y), (float) y));

            for (int i = 0; i < 1000; i++)
            {
                x = Math.abs(random.nextLong()) % lm;
                y = Math.abs(random.nextLong()) % lm;

                assertEquals(x + " / " + y + " % " + lm, x, (long) math.modMultiply(math.modDivide((float) x, (float) y), (float) y));
            }
        }
    }

    public static void testPow()
    {
        FloatModMath math = new FloatModMath();
        math.setModulus(MODULUS[0]);

        assertEquals("no overflow", 3125, (long) math.modPow((float) 5, (float) 5));
        assertEquals("overflow", ((long) MODULUS[0] + 1) / 2, (long) math.modPow((float) 2, (float) -1));
    }

    public static void assertEquals(String message, long expected, long actual)
    {
        if (expected != actual)
        {
            System.out.println(message + " expected: " + expected + " actual: " + actual);
        }
    }
}