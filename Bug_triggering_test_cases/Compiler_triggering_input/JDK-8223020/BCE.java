public class BCE {

    final static int MAX = 1024 * 16;
    private static int total = 0;
    private static final int[] numbers = new int[32];

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < MAX; i++) {
            try { addAll(-16, 16); } catch (Exception e) {}
            try { addAll(0, 32); } catch (Exception e) {}
            try { addAll(16, 48); } catch (Exception e) {}
        }

        Thread.sleep(4000);
        System.out.println("total = " + total);
    }

    public static void addAll(int x, int y) {
        for (int i = x; i < y; i++) {
            total += numbers[i];
        }
    }

}