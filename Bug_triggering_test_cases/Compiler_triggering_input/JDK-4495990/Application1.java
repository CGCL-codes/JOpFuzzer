public class Application1 {
    boolean packFrame = false;

    /**Construct the application*/
    public Application1() {
    }

    void testThread(int size) {
        int i=0;
        while (i < size) {
            TT tt = new TT();
            tt.setLoop(i * 11);
            tt.start();
            i++;
        }
    }


    /**Main method*/
    public static void main(String[] args) {
        Application1 ap = new Application1();

        ap.testThread(100);
    }


    class TT extends Thread {


        int loop=0;

        public void setLoop(int l) {
            loop = l;
        }

        public void run() {
            int j =0;
            try {
                while (j < loop) {
                    if (j % 50 == 0)
                        System.out.println("I am " + this + " " + j);
                    j++;
                }

            } catch (Exception e) {

            } finally {
                System.out.println (" i die " + this);
// the following throw can cause the VM to crash
                try {
                    throw new Exception("Stack Cleanup");
                } catch (Exception e) {
                    // Clean!
                }
            }
        }
    }
}