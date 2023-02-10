public class Test {
    public static void main(String[] args)
    {
        try { (new byte[-1])[0] = 0; }
        catch (Exception e) { System.out.println(e); }
    }
}