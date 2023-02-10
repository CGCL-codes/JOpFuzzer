public class SigILL {
    public static void main(String[] args) {
        int res = 0;
        for (int i=0; i<2000; i++) {
            for (int j=0; j < 2000; j++) {
                res += i | (j >>> 53);
            }
        }
        System.out.println("res = " + res);
    }