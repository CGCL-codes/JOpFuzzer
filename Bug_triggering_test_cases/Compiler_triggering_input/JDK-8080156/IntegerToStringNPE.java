public class IntegerToStringNPE {
    public static void main(String[] args) {
        for (int i = 0; i < 1000000; i++) {
            String a = doStuff();
        }
    }

    public static String doStuff() {
        int a = Integer.MIN_VALUE;
        int bounds = (int) (Math.random() * 150);
        for (int i = 0; i < bounds; i++) {
            int random = (int) (Math.random() * 1000);
            if (random > a) {
                a = random;
            }
        }

        try {
            return Integer.toString(a);
        } catch (NullPointerException e) {
            System.err.println("NPE converting " + a + " to a String!");
            throw e;
        }
    }
}