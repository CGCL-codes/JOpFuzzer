import java.util.*;
public class SelfTest3 {
    
    private static final int BLOCK_SIZE = 1_000;
    public static ArrayList<byte[]> d = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        System.out.print("Test 3: just allocating memory");
        int cycle = 0;
        while (true) {
            for (int z = 0; z < 100; ++z) {
                for(int i = 0; i < BLOCK_SIZE; ++i) {
                    d.add(new byte[10000]);
                }
                if (d.size() > BLOCK_SIZE * 10) {
                    d.subList(0, BLOCK_SIZE).clear();
                }
            }
            System.out.println("Cycle " + cycle++);
        }
    }
}
