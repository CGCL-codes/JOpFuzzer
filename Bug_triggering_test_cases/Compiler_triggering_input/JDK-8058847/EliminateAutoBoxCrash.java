public class EliminateAutoBoxCrash {
    private static final int[] values = new int[256];

    public static void main(String[] args) {
        byte[] bytes = new byte[] {-1};
        while (true) {
            for (Byte b : bytes) {
                values[b & 0xff]++;
            }
        }
    }
}
