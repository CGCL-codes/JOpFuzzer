class TesterSmall_Class_0 {
    static long var_bad = -1L;

    public TesterSmall_Class_0()
    {
        var_bad >>= 65;
        var_bad /= 65;
    }
}

public class TesterSmall {
    public static void main(String[] args)
    {
        TesterSmall_Class_0 t = new TesterSmall_Class_0();

        long var_ok = -1L;
        var_ok >>= 65;
        var_ok /= 65;

        System.out.println("var_bad = " + TesterSmall_Class_0.var_bad);
        System.out.println("var_ok = " + var_ok);
    }
}