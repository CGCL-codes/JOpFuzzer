public class UnlockPrevents
{
    public static void main(String args[])
    {
        for (int n = 0; n < 5; n++)
            doit();
    }

    public static void doit()
    {
        for (int n = 0; n < 10000; n++) {
            Histogram h = new Histogram();

            assertTrue(h.foo() == 1);
            h.add(3);
            assertTrue(h.bar() == 0);
        }
    }

    static void assertTrue(boolean x)
    {
    }
}

class Histogram
{
    private int count;
    private double min;

    public synchronized void add(double value)
    {
        if (count++ == 0) {
            min = value;
        } else {
            if (min > value)
                min = value;
        }
    }

    public int foo()
    {
        return 1;
    }

    public synchronized int bar()
    {
        return 0;
    }
}
