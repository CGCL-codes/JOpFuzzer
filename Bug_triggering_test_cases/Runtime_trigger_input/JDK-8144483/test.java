
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarFile;

public class GcRotationPause {

private static final int allocCount = 100000;

/* Create some threads to generate thread stack maps.
 * Threads will be started and sleep for the given time.
 * No CPU activity is needed.
 */
public static void startThreads(int num, final long duration) {
    int i;
    for (i = 0; i < num; i++) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.currentThread().sleep(duration);
                } catch (InterruptedException ex) {
                    System.err.println(ex);
                }
            }
        }).start();
    }
}

/* Create class loaders that search a jar file
 * and look up a non-existing resource, so that
 * the jar file gets mapped into memory
 */
public static URLClassLoader[] openJars(int num, String jarName) {
    int i;
    URLClassLoader[] result = new URLClassLoader[num];
    for (i = 0; i < num; i++) {
        try {
            String file = i + "-" + jarName;
            result[i] = new URLClassLoader(new URL[]{(new File(file)).toURI().toURL()});
            result[i].findResource("does.not.exist");
        } catch (MalformedURLException ex) {
            System.err.println(ex);
        }
    }
    return result;
}

/* Loop for some time and do some allocation
 * to generate GC log activity and rotation.
 * The hashCode() and println() is just some
 * fuzzy code to prevent removal of the code
 * by the JIT
 */
public static void loopAllocation(long duration) {
    long until = System.currentTimeMillis() + duration;
    long now;
    int i;
    while ((now = System.currentTimeMillis()) < until) {
        for (i = 0; i < allocCount; i++) {
            Object o = new Object();
            if (o.hashCode() == 0) {
                System.out.println("Prevent JIT code removal");
            }
        }
    }
}

public static void main(String[] args) {
    int threadCount = Integer.parseInt(args[0]);
    long duration = Long.parseLong(args[1]);
    int jarCount = Integer.parseInt(args[2]);
    String jarName = args[3];
    System.out.println("Starting " + threadCount + " threads, each keeping them for " + duration + " ms.");
    startThreads(threadCount, duration);
    System.out.println("Opening " + jarCount + " jars based on " + jarName + ".");
    URLClassLoader[] cls = openJars(jarCount, jarName);
    loopAllocation(duration);
    System.out.println("Done.");
}
}

