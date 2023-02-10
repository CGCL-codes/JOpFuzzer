public class TestACSameSrcDst {
    static int test1(int[] src, int[] dst) {
        System.arraycopy(src, 5, dst, 0, 10);
        return dst[0];
    }

    static int test2(int[] src) {
        System.arraycopy(src, 0, src, 0, 10);
        return src[0];
    }

    public static void main(String[] args) {
        int[] array = new int[15];
        for (int i = 0; i < 20000; i++) {
            for (int j = 0; j < array.length; j++) {
                array[j] = j;
            }
            int expected = array[5];
            int res = test1(array, array);
            if (res != expected) {
                throw new RuntimeException("bad result: " + res + " != " + expected);
            }
            test2(array);
        }
    }
}