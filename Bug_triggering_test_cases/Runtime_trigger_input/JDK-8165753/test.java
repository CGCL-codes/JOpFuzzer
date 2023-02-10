
<code>
public class TestSecurityManager extends SecurityManager {

    public TestSecurityManager() {
        super();
    }

    public void checkPermission(Permission perm)
    {
        System.err.println("TestSecurityManager.checkPermission: " + perm.getClass().getName() + " / " + perm.getName() + " / " + perm.getActions());
        super.checkPermission(perm);
    }
}
</code>
