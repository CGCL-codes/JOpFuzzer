

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Date;

public class First {
    
    public static void main(String... args) {
        int classesToLoad = 25000; 
        System.out.println("% iter#  :   time in sec");
        System.out.println("......................");
        long initTime =  new Date().getTime();
        long time = initTime;
        for (int i = 1; i < classesToLoad; i++) {
            loadClass();
            if (i % 1000 == 0) {
                time = new Date().getTime();
                System.out.println("%  " + i + "  :  " + (time - initTime)/1000);
            }
        }
        if (time - initTime > 120_000) {
            System.out.println("Test failed: it takes to long to load " + classesToLoad + " classes");
        } else {
            System.out.println("Test passed");
        }
        
    }
    static private int counter = 0;
    protected static void loadClass() {
            try {
                String jarUrl = "file:" + counter + ".jar";
                counter++;
                URL[] urls = new URL[]{new URL(jarUrl)};
                URLClassLoader cl = new URLClassLoader(urls);
                Foo foo = (Foo) Proxy.newProxyInstance(cl,
                        new Class[]{Foo.class},
                        new FooInvocationHandler(new FooBar()));
            } catch (java.net.MalformedURLException badThing) {
                // should never occur
                System.err.println("Unexpeted error: " + badThing);
                throw new RuntimeException(badThing);
            }
        }      
        
    public static interface Foo {
    }

    public static class FooBar implements Foo {
    }

    static class FooInvocationHandler implements InvocationHandler {
        private final Foo foo;

        FooInvocationHandler(Foo foo) {
            this.foo = foo;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return method.invoke(foo, args);
        }
    }

}
