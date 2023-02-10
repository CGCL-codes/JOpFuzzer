import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;

public class TestCgroup {
    private static OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(
            OperatingSystemMXBean.class);

    public static void main(String[] args) {
        new Thread(() -> {
            int i = 0;
            while (true) {
                i++;
                if (i > 10000) {
                    i = i - 1000;
                }
            }

        }).start();

        while (true) {


            System.out.println("process:"+osBean.getProcessCpuLoad() * 100);

            System.out.println("system"+osBean.getSystemCpuLoad() * 100);
            System.out.println(osBean.getAvailableProcessors());
            try {
                Thread.sleep(3000);

            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

    }
}