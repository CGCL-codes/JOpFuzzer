public class JniStaticContextFloat {

    final static int Count = 1024;

    public static synchronized native float staticMethodFloat1(float j0, float j1, float j2, float j3);

    public static void main(String[] args) throws Exception {
        System.load("/home/yangfei/test/JNI/libJniStatic.so");

        for (int c = 0; c < Count; c++) {
            Thread t = new Thread(new Runnable() {
                public void run() {
                    float d = JniStaticContextFloat.staticMethodFloat1((float) (1), (float) (2), (float) (4), (float) (8));
                }
            });
            t.start();
        }

        Thread.sleep(1000);

        System.out.println("PASS!");
    }

}