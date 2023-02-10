import java.util.ArrayList;
import java.util.List;
import java.net.InetAddress;
public class LocalIpTest {

    private static final List<InetAddress> addrs = getLocalIp();

    public static void main(String[] args) {
        if (canPerformHostCheck()) {
            String expectedName = addrs.get(0).getHostName();
            System.out.println("expected name = " + expectedName);
            String expectedIp = addrs.get(0).getHostAddress();
            System.out.println("expected ip = " + expectedIp);
        }
    }

    private static boolean canPerformHostCheck() {
        return !addrs.isEmpty();
    }

    private static List<InetAddress> getLocalIp() {
        try {
            List<InetAddress> addrs = new ArrayList<>();
            InetAddress localHost = InetAddress.getLocalHost();
            if (!localHost.isLoopbackAddress()) {
               addrs.add(localHost);
            }
	    return addrs;
        } catch (Exception e) {
            throw new RuntimeException("Fail", e);
        }
    }
}
