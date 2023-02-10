class TesterSmall_Class_0 {
    static long var_1 = -1;

    public TesterSmall_Class_0()
    {
        long var_2 = TesterSmall_Class_0.var_1 * 1;

        var_2 &= (long)(new byte[(byte)1E10])[(byte)~var_2];
    }
}

public class TesterSmall {
    public static void main(String[] args)
    {
        try {
            TesterSmall_Class_0 t = new TesterSmall_Class_0();
        } catch (Throwable e) {
            System.out.println("Got exception " + e);
        }
    }
}