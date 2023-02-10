import java.util.Arrays;
public class SDD1 {
    public static void main(String[] args) throws Throwable {
        try {
            for (int i = 0; i < 100; ++i) {
                Thread t = new Thread(new Runnable() {
                  public void run() {
                     LoadSplitThruPhi.main(args);
                  }
                });
                t.start();
                Thread.sleep(100);
                t.stop();
                t.join();
                System.out.println("iter " + i);
            }
        } catch (AssertionError e) {
            System.out.println("Assertion Error");
        } catch (Exception ex) {
            System.out.println(ex.getClass().getCanonicalName());
        } catch (OutOfMemoryError e) {
            System.out.println("OOM Error");
        }
    }
}
class LoadSplitThruPhi {
    public static void getPermutations (byte[] inputArray, byte[][] outputArray) {
        int[] indexes = new int[]{0, 2};
        for (int a = 0; a < (int)(a + 16); a++) {
            int oneIdx = indexes[0]++;
            for (int b = a + 1; b < inputArray.length; b++) {
                int twoIdx = indexes[1]++;
                outputArray[twoIdx][0] = inputArray[a];
                outputArray[twoIdx][1] = inputArray[b];
            }
        }
    }
    public static void main (String[] args) {
        byte[] inputArray = new byte[]{0, 1};
        byte[][] outputArray = new byte[3][2];
        for (int i = 0; i < 1000000; i++) {
            getPermutations(inputArray, outputArray);
        }
    }
}
