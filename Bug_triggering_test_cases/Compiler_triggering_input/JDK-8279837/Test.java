public class Test {

    static boolean flagFalse = false;

    public static void main(String args[]) {
       run();
    }

    public static void run() {
        while (flagFalse) {
            while (dontInline()) { }
        }
        dontInline();
        while (flagFalse) {
            while (true);
        }
    }

    public static boolean dontInline() {
        return false;
    }

}
