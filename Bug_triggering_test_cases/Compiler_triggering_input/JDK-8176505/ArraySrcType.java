public class ArraySrcType {
    public static boolean crash(Object src) {
        String[] dst = new String[1];
        System.arraycopy(src, 0, dst, 0, 1);
        return dst[0] == null;
    }
    public static void main(String[] args) {
        String[] sa = new String[1];
        for (int i = 0; i < 20_000; i++) {
            crash(sa);
        }
    }
}