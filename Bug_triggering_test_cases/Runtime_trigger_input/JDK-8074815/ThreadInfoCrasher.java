
package lib.timer.impl;

import java.lang.management.ManagementFactory;

public class ThreadInfoCrasher {
    public static void main(String[] args) throws Exception {
        new Thread() {
            @Override
            public void run() {
                while (true) ManagementFactory.getThreadMXBean().getThreadInfo(new long[] {}, 1);
            }
        }.start();

        while (true) {
            new byte[10_000].clone();
        }
    }
}

