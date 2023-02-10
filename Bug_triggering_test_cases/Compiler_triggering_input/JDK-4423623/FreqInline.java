public class FreqInline {
    public int target(int i, int j) {
        if( i < j )
            return i - j;
        else if( i > j )
            return j - i;
        else
            return j + i;
    }

    public void caller() {
        for(int i = 0; i < 1000000; i++)
            target(i, i + 1);

        for(int i = 0; i < 10; i++)
            target(i,i);
    }

    public static void main(String[] args) {
        FreqInline frIn = new FreqInline();
        for(int i = 0; i < 100000; i++)
            frIn.caller();
    }
}
