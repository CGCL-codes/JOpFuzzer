
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;

public class MetaspaceMaxMain {

    public static void main(String[] args) {
        MemoryPoolMXBean metaspaceMemoryPool = ManagementFactory.getPlatformMXBeans(MemoryPoolMXBean.class)
                .stream()
                .filter(pool -> "Metaspace".equals(pool.getName()))
                .findFirst()
                .orElseThrow();
        System.out.println("Metaspace pool max: " + metaspaceMemoryPool.getUsage().getMax());
    }
}

