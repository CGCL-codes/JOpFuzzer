import java.util.Random;
import sun.management.*;
import com.sun.management.*;

public class jmmOOM {
  private static final String FLAG = "HeapDumpPath";

  public static void main(String[] args) {
    Random rng = new Random();
    HotSpotDiagnosticMXBean bean = ManagementFactoryHelper.getDiagnosticMXBean();
    while (true) {
        String str = new String("" + rng.nextInt());
        bean.setVMOption(FLAG, str);
    }
  }
}
