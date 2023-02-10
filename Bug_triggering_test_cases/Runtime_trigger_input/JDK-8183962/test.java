import java.security.AccessController;
import java.security.PrivilegedAction;

public class Test {
 interface VoidPrivilegedAction extends PrivilegedAction<Void> {
   void perform();

   @Override
   default Void run() {
     perform();
     return null;
   }
 }

 static void doPrivileged(VoidPrivilegedAction act) {
   AccessController.doPrivileged(act);
 }

 public static void main(String[] args) throws Exception {
   doPrivileged(() -> System.out.println(System.getProperty("java.home")));
 }
}
