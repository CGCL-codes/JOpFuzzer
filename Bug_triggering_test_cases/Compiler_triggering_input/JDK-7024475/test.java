import java.net.*;
import org.jsuffixarrays.*;

public class test extends Thread {
    public static void main(String[] args) throws Exception {
        int hung = 0;
        for (int i = 0; i < 10000; i++) {
            URLClassLoader apploader = (URLClassLoader)test.class.getClassLoader();
            URLClassLoader ucl = new URLClassLoader(apploader.getURLs(), apploader.getParent());
            Class c = ucl.loadClass("test");
            Thread t = (Thread)c.newInstance();
            t.start();
            for (int s = 0; s < 10; s++) {
                Thread.sleep(500);
                if (!t.isAlive()) {
                    break;
                }
            }
            if (t.isAlive()) {
                hung++;
            }
            System.out.println("ok " + (i + 1 - hung) + " hung " + hung);
        }
    }
    public void run() {
        DivSufSortTest t = new DivSufSortTest();
        t.setupForConstraints();
        t.invariantsOnRandomLargeAlphabet();
        t.sameResultWithArraySlice();
        t.invariantsOnRandomSmallAlphabet();
    }
}