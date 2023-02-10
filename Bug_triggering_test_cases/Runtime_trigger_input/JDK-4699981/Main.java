
public class Base {
}

public class Derived extends Base {
}

public class Support {
    private Base base = new Base();
}


import java.net.URL;
import java.net.URLClassLoader;

/**
 * @version $Revision$
 */
public class Main {
    public static void main(String[] args) throws Exception {
        new Main();
    }

    private Object lock = new Object();

    public Main() throws Exception {
        URL location = getClass().getProtectionDomain().getCodeSource
                ().getLocation();
        URLLoader loader = new URLLoader(new URL[]{location}, getClass
                ().getClassLoader().getParent());

        Class cls = loader.loadClass("Support");

        Thread t1 = new Thread(new Run1(cls));
        t1.start();

        Thread.sleep(1000);

        // Load Derived, will trigger a loadClassInternal for BaseClass.java
        loader.loadClass("Derived");
    }

    public class URLLoader extends URLClassLoader {
        private boolean m_firstTime = true;

        public URLLoader(URL[] urls, ClassLoader parent) {
            super(urls, parent);
        }

        public Class loadClass(String name) throws ClassNotFoundException {
            if (name.equals("BaseClass.java")) {
                if (m_firstTime) {
                    m_firstTime = false;

                    // Notify the other thread
                    synchronized (lock) {
                        lock.notifyAll();
                    }

                    // Wait on the classloader to have the JVM throw
                    ClassCircularityError
                    try {
                        synchronized (this) {
                            wait(5000);
                        }
                    } catch (InterruptedException ignored) {
                    }
                }
            }
            return super.loadClass(name);
        }
    }

    public class Run1 implements Runnable {
        private Class cls;

        public Run1(Class cls) {
            this.cls = cls;
        }

        public void run() {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException ignored) {
                }
            }

            // Trigger loadClassInternal for BaseClass.java
            try {
                cls.newInstance();
            } catch (Throwable x) {
                x.printStackTrace();
            }
        }
    }
}
