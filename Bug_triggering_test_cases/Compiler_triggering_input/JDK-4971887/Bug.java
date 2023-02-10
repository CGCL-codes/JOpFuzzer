public class Bug
{
    public static double calcTotal ( long[] counts )
    {
        double total = 0;
        for ( int i=0; i < counts.length; i++ )
        {
            total += counts[i];
        }
        return ( total );
    }

    public static void main ( String[] args )
    {
        long[] counts = new long[] {
                113,64,23,56,98,48,33,101,8,14,27,58,17,6,
                121,18,3,3,24,13,58,14,23,14,4,123,29,5 };
        double total = Bug.calcTotal( counts );
        System.out.println( "total=" + total );
    }
}