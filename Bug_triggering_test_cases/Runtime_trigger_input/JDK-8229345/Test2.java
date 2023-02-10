import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;

public class Test2 extends Thread {

   private static void printMemoryUsage(MemoryUsage u) {
      System.out.println(  "init="   + u.getInit()/1024
                       + "K,commit=" + u.getCommitted()/1024
                       + "K,used="   + u.getUsed()/1024
                       + "K,max="    + u.getMax()/1024
                       + "K,max="    + u.getMax());
   }
   public void run() {

      List<MemoryPoolMXBean> mbeans = ManagementFactory.getMemoryPoolMXBeans();
      MemoryPoolMXBean ccache=null;
      for (MemoryPoolMXBean m: mbeans) {
        if( m.getName().equals("Code Cache")) {
          ccache = m;
          break;
        }
      }
      
      long t1 = System.currentTimeMillis();
      while (true) {
          long t = System.currentTimeMillis() -t1;
          System.out.print(t + " getUsage():");     printMemoryUsage(ccache.getUsage());
       // System.out.print("getPeakUsage():"); printMemoryUsage(ccache.getPeakUsage());
          try { Thread.sleep(3000); } catch (Exception ex) {}
      }
   } 


    public static void main(String args[]) throws Exception { 

        (new Test2()).start();
        URL url = Test.class.getProtectionDomain().getCodeSource().getLocation();

        for (int j=0; j<100000000; j++) {
            ClassLoader cl;
            Class clazz;

            cl = new MyClassLoader(url);
            clazz = cl.loadClass("SomeClass");
            refClass(clazz);

            cl = new MyClassLoader(url);
            clazz = cl.loadClass("SomeClass");
            refClass(clazz);

//          if (j%10000==0) System.gc();
//          Thread.sleep(100);
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
                return Class.forName(name, resolve, Test.class.getClassLoader());
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
