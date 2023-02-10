import java.util.ArrayList;
import java.util.List;

public class TestJVMCrash<T> {
    public static void main(String[] args) {
        int[] intArray = new int[]{1, 2, 3};
        long[] longArray = new long[]{1L, 2L, 3L};

        while (true) {
            justWasteMomory();

            clone(intArray, ARRAY_TYPE.INT_ARRAY, intArray.length); // must both int & long
            clone(longArray, ARRAY_TYPE.LONG_ARRAY, longArray.length); // must both int & long
        }
    }

    public enum ARRAY_TYPE {
        INT_ARRAY, LONG_ARRAY;
    }

    private static<T> T clone(T src, ARRAY_TYPE type, int length2) {

        final int length = getLength(type, src);
        final Object dest = createArray(type, length);
        //
        System.arraycopy(src, 0, dest, 0, length);
        return (T) dest;
    }

    private static<T> T createArray(ARRAY_TYPE type, int length) { // synchronized OK

        switch (type) {
            case INT_ARRAY :
                return (T) new int[length];
            case LONG_ARRAY :
                return (T) new long[length];
            default :
                throw new RuntimeException("Error in createArray");
        }
    }

    private static int getLength(ARRAY_TYPE type, Object array) { // synchronized OK
        switch (type) {
            case INT_ARRAY :
                return ((int[]) array).length;
            case LONG_ARRAY :
                return ((long[]) array).length;
            default :
                throw new RuntimeException("Error in getLength");
        }
    }

    private static void justWasteMomory() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add("Nothing here, just waist memory. please wait 10 seconds and will see what happen");
            list.add("Andy gc(both G1 & -XX:+UseParallelGC) will crash the jvm");
            list.add("Andy gc(both G1 & -XX:+UseParallelGC) will crash the jvm");
            list.add("Andy gc(both G1 & -XX:+UseParallelGC) will crash the jvm");
            list.add("Andy gc(both G1 & -XX:+UseParallelGC) will crash the jvm");
        }
    }
}
