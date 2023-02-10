
public class Bug {

    static Object m1(boolean var0) {
        throw new NullPointerException();
    }

    static boolean m2(boolean var0) {
        boolean var1 = false;

        try {
            var1 = m2(var0);
        } catch (StackOverflowError e) {
            return true;
        }
//        System.out.println("This is the only diff");
        if (var1) {
            try {
                m1(var0);
            } catch (StackOverflowError e) { }
        }
        return false;
    }

    public static void main(String[] var0) {
        m2(true);
    }
}
