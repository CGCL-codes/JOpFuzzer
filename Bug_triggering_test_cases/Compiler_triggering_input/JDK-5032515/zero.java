public class zero {
    int a;
    int b;
    long c;
    long d;
    public static void main(String[] args) {
        zero zero = new zero();
        System.out.println(zero);
        for (int i = 0; i < 1000000; i++) {
        }
        zero.a = 0;
        zero.b = 1;
        zero.c = 0xffffffffffL;
        zero.d = 0xfffffffff0L;
        System.out.println(zero);
        if (args.length == 0) {
            zero.a = 0;
            zero.b = 0;
            zero.c = 0xffffffffffL;
            zero.d = 0xfffffffff0L;
        } else if (args.length == 2) {
            zero.a = 1;
            zero.b = 1;
            zero.c = 0xffffffffffL;
            zero.d = 0xffffffffffL;
        } else if (args.length == 3) {
            zero.a = 0;
            zero.b = 0;
            zero.c = 0xfffffffff0L;
            zero.d = 0xfffffffff0L;
        } else if (args.length == 4) {
            zero.a = 1;
            zero.b = 1;
            zero.c = 0xffffffffffL;
            zero.d = 0xfffffffff0L;
        } else if (args.length == 5) {
            zero.a = 0;
            zero.b = 1;
            zero.c = 0xffffffffffL;
            zero.d = 0xfffffffff0L;
        }
    }
}