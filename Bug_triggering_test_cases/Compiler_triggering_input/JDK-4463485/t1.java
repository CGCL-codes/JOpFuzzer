class c
{
    private int val;

    void setVal( int x )
    {
        this.val = x;
    }
}

public class t1
{

    public static void main( String[] args )
    {
        final int n = 100000000;

        for ( int i = 0; i < n; i++ ) {
            c aC = new c();
            aC.setVal( i );
        }
    }
}