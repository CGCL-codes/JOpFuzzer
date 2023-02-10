public class test {
    public static void main(String args[]) {
            float zerof = + 0.0f;
            double zerod = + 0.0d;
            String s=Float.toString(0.0f - zerof);
            if (s.startsWith("-"))
                System.out.println("Failed: "+s);
            else
                System.out.println("Passed: "+s);
    }
}