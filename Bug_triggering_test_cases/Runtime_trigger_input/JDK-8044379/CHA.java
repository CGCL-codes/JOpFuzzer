
public class CHA {

    static Operation operation;
    static int result;

    public static void main(String[] args) throws Exception {
        operation = new AddOperation();
        benchmark();
    }

    public static void benchmark() throws Exception {
        for (int i = 0; i < 10; i++) {
            if (i == 7) Class.forName("SubstractOperation");
            long start = System.nanoTime();
            for (int j = 0; j < 1000000; j++) {
                result = operation.perform(j, 1);
            }
            long end = System.nanoTime();
            System.out.println((end - start) + " ns");
        }
        System.out.println(result);
    }
}

interface Operation {
    int perform(int a, int b);
}

class AddOperation implements Operation {

    static {
        System.out.println("AddOperation loaded");
    }

    @Override
    public int perform(int a, int b) {
        return a + b;
    }
}

class SubstractOperation implements Operation {

    static {
        System.out.println("SubstractOperation loaded");
    }

    @Override
    public int perform(int a, int b) {
        return a - b;
    }
}

