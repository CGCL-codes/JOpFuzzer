import java.security.Permission;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

public class Test {
    public static void main(String... args) {
        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkAccess(ThreadGroup g) {System.out.println("Test.checkAccess");}
            @Override
            public void checkAccess(Thread t1) {System.out.println("Test.checkAccess");}
            @Override
            public void checkPermission(Permission perm) {System.out.println("Test.checkPermission");}
        });
        List.of(
          Thread.ofVirtual(),
          Thread.ofVirtual().scheduler(new Executor() {
                @Override public void execute(Runnable command) {
                    Thread.ofPlatform().start(command);
                }}
            )
        ).forEach(b -> b.start(() -> new ThreadGroup("group")));
    }
}