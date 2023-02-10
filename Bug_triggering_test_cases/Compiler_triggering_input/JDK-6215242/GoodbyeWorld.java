public class GoodbyeWorld {

    public static double func1(){
        double r = 0;
        for (int i = 0; i < 1; i++)
            r += func2();
        return r;
    }

    public static double func2(){
        double[] a = {0};
        double r = 0;
        for (int i = 0; i < 1; i++)
            r = a[i];
        return r;
    }

    public static void main(String[] argv) {
        for (int i = 0; i < 1000; i++)
            func1(); // System.out.println("i["+i+"]=" + func1());
    }
}
