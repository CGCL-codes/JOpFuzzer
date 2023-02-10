final class TesterSmall_10_Class_0 {
    static float var_1 = 1;
    float var_2 = 1;
}

public class TesterSmall_10 {
    float var_3 = TesterSmall_10_Class_0.var_1; // changing this line to "float var_1 = 1" will make the test pass
    float var_bad;
    float var_ok;

    void test()
    {
        var_bad += 1 / -(TesterSmall_10_Class_0.var_1 -= TesterSmall_10_Class_0.var_1);

        TesterSmall_10_Class_0 t0 = new TesterSmall_10_Class_0();
        var_ok += 1 / -(t0.var_2 -= t0.var_2);
    }

    public static void main(String[] args)
    {
        TesterSmall_10 t = new TesterSmall_10();
        t.test();

        System.out.println(" Tester.var_ok = " + t.var_ok);
        System.out.println("Tester.var_bad = " + t.var_bad);

        System.out.println((t.var_bad == t.var_ok ? "PASS" : "FAIL") + "\n");
    }
}