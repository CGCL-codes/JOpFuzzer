public class p {
    public static void main(String[] args) {
        while (true) {
            for (int i = 0; i < args.length; i++) {
                args[i] = new String(args[i]);
            }
        }
    }
}