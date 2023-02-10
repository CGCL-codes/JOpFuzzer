public class SyncJSR {
    private int count = 0;
    private int[] ia = new int[1];

    public synchronized void calc() {
        try {
            count++;
        } finally {
            // always throws a NullPointerException
            ia[0] = 1;
        }
    }

    public void calcCatch() {
        try {
            calc();
        } catch (NullPointerException ex) {
            // catch the exception thrown in calc()
        }
    }

    public static void main(String[] args) {
        SyncJSR obj = new SyncJSR();

        for (int i = 0; i < 200000; i++) {
            obj.calcCatch();
        }
        obj.ia = null;
        obj.calcCatch();

        try {
            // Usually, this crashes the VM if the object was not unlocked correctly
            obj.wait(1000);
        } catch (Exception ex) {
        }
    }
}