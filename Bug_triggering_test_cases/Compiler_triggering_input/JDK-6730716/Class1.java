public class Class1
{
    private static boolean compare(ThreeClass threeClass, FiveClass fiveClass)
    {
        for (;;)
        {
            if (threeClass == null || fiveClass == null)
            {
                return (Object)threeClass == (Object)fiveClass;
                // Using this line it works
                //return threeClass == null && fiveClass == null;
            }
            threeClass = threeClass.next;
            fiveClass = fiveClass.next;
        }
    }

    public static void main(String[] args)
    {
        System.out.println( "Number of cpus = " + Runtime.getRuntime().availableProcessors() );
        ThreeClass threeClass = new ThreeClass();
        threeClass.next = null;
        FiveClass fiveClass = new FiveClass();
        fiveClass.next = null;

        for ( int i = 0; i < 10000; i++ )
        {
            if ( !Class1.compare(threeClass, fiveClass) )
            {
                System.out.println( "Failed at i = " + i );
                break;
            }
        }
    }
}
class ThreeClass
{
    ThreeClass next;
}
class FiveClass
{
    FiveClass next;
}