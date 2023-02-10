
import java.net.URL;
import java.net.URLClassLoader;

public class NmethodLogTest {

    public static void main(String args[]) throws Exception { 

        URL url = NmethodLogTest.class.getProtectionDomain().getCodeSource().getLocation();

        for (int j=0; j<500; j++) {
            ClassLoader cl;
            Class clazz;

            cl = new MyClassLoader(url);
            clazz = cl.loadClass("SomeClass");
            refClass(clazz);

            cl = new MyClassLoader(url);
            clazz = cl.loadClass("SomeClass");
            refClass(clazz);
        }
    }

    private static void refClass(Class clazz) throws Exception {
        java.lang.reflect.Field name = clazz.getDeclaredField("NAME");
        name.setAccessible(true);
        name.get(null);
    }

    private static class MyClassLoader extends URLClassLoader {
        public MyClassLoader(URL url) {
            super(new URL[]{url}, null);
        }
        protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            try {
                return super.loadClass(name, resolve);
            } catch (ClassNotFoundException e) {
                return Class.forName(name, resolve, NmethodLogTest.class.getClassLoader());
            }
        }
        protected void finalize() throws Throwable {
            super.finalize();
        }
    }
}

abstract class Foo {
    public abstract int foo();
}
class Foo1 extends Foo {
    private int a;
    public int foo() { return a; }
}
class Foo2 extends Foo {
    private int a;
    public int foo() { return a; }
}
class Foo3 extends Foo {
    private int a;
    public int foo() { return a; }
}
class Foo4 extends Foo {
    private int a;
    public int foo() { return a; }
}

class SomeClass {
    static final String NAME = "name";

    static {
        int res =0;
        Foo[] foos = new Foo[] { new Foo1(), new Foo2(), new Foo3(), new Foo4() };
        for (int i = 0; i < 100000; i++) {
            res = foos[i % foos.length].foo();
        }
    }
}
