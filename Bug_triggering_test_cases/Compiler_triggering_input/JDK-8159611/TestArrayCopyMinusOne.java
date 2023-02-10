public class TestArrayCopyMinusOne{

    public boolean do_test() {
        try {
            System.arraycopy(new Object[1], 1, new Object[1], 1, -1);
            return false;
        } catch (IndexOutOfBoundsException e) {
            return true;
        }
    }


    static final int loop_cnt=100000;

    public static void main(String args[]){
        TestArrayCopyMinusOne xyz = new TestArrayCopyMinusOne();
        int errors = 0;
        long duration = System.nanoTime();
        for (int x = 0; x < loop_cnt; x++) {
            if (!xyz.do_test()) errors++;
        }
        duration = System.nanoTime() - duration;
        System.out.println("errors: " + errors + " (duration: " + duration/loop_cnt + ")");
    }

}