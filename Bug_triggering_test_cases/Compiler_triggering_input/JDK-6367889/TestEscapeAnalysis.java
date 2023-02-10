import java.util.*;
import java.text.*;
import java.io.*;

public class TestEscapeAnalysis
{
    private static final int COUNT = 1000000000;
    public static void main(String[] args) throws Exception {
        test();
        test();
        test();
    }

    private static void test() {
        int x = 0;
        Object lock = new Object();
        long ts = System.currentTimeMillis();
        for (int i=0; i<COUNT; i++) {
            synchronized (lock) {
                x ++;
            }
        }
        long te = System.currentTimeMillis();
        System.out.println(x+", time="+(double)(te-ts)/1000.0);
    }
}