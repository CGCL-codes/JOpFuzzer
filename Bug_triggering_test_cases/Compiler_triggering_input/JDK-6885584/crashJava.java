public class crashJava
{

    public void crashJava()
    {
        // This number must be more than 14562
        for(int j = 14563; j != 0; j--)
        {
        }

        // This must reference a field
        System.out.println(i1);

        // The resource leak is roughly proportional to this initial value
        for(int k = 20000000; k != 0; k--)
        {
            if(i2 > i3)i1 = k;
            if(k==(20000000-1))break;
        }

        System.out.println("program ended :)");
    }

    public static void main(String args[])
    {
        (new crashJava()).crashJava();
    }

    private int i1;
    private int i2;
    private int i3;
}