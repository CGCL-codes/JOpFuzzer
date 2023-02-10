import java.util.ArrayList;
import java.util.List;

public class ArrayListGrow {
    static final int SIZE = 2048;
    static volatile List<Object> tmp;

    static void runTest() {
        List<Object> list = new ArrayList<>(SIZE);
        fill(list);
        tmp = list;
    }

    static void fill(List<Object> list) {
        for (int i = 0; i < SIZE; i++) {
            list.add(i);
        }
    }

    static void spoil() {
        for (int i = 0; i < 1000000; i++) {
            new ArrayList<>(0).add("");
        }
    }

    public static void main(String[] args) {
        spoil();

        while (true) {
            runTest();
        }
    }
}