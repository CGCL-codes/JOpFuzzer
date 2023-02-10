import java.security.*;

public class Test {
    public static void main(String[] args) throws Exception {
        System.setSecurityManager(new SecurityManager());
        Permissions perms = new Permissions();
        perms.add(new AllPermission());
        ProtectionDomain pd = new ProtectionDomain(null, perms, null, null);
        AccessControlContext acc = new AccessControlContext(new ProtectionDomain[] { pd });

        AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                return System.getProperty("java.version");
            }
        }, acc);
    }
}
