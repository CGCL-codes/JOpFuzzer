public class crash {
    public static void main(String[] args) {
        for (int i = 0; i < 100000; i++) {
            intToLeftPaddedAsciiBytes();
        }
    }

    public static int intToLeftPaddedAsciiBytes() {
        int offset = 40;
        int q;
        int r;
        int i = 100;
        int result = 1;
        while (offset > 0) {
            q = (i * 52429) >>> (16+3);
            r = i - (q * 10);
            offset--;
            i = q;
            if (i == 0) {
                break;
            }
        }
        if (offset > 0) {
            for(int j = 0; j < offset; j++) {
                result++;
            }
        }
        return result;
    }


}
