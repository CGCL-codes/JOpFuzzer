public class CharArrayCrash {
    static char[] pattern0 = {0};
    static char[] pattern1 = {1};

    static void test(char[] array) {
        if (pattern1 == null) return;

        int i = 0;
        int pos = 0;
        char c = array[pos];

        while (i >= 0 && (c == pattern0[i] || c == pattern1[i])) {
            i--;
            pos--;
            if (pos != -1) {
                c = array[pos];
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000000; i++) {
            test(new char[1]);
        }
    }
}
