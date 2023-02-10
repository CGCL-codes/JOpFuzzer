public class TestLongLoop
{
    public static Integer getInteger()
    {
        long index;
        for(index = 10 ; index < Long.MAX_VALUE ; index++)
        {
            if (index > 2000) return new Integer((int)index); // (1)
        }

        System.out.println("INDEX is " + index);
        return null;
    }

    public static void main(String[] args)
            throws Exception
    {
        for(int i = 0 ; i < 1000 ; ++i) // (2)
        {
            Integer x = getInteger();
            if(x==null)
            {
                System.out.println("GOT null after " + i + " iterations!");
                System.exit(20);
            }
        }
    }
}