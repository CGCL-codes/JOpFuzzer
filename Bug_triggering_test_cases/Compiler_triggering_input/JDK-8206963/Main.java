import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
public class Main{
    public static void main(String[] args)throws Exception{
        File file1=new File("./sub1");
        File file2=new File("./sub2");
        URL url1 = file1.toURI().toURL();
        URL url2 = file2.toURI().toURL();
        changeT2(url1); // run with loader1
        run(url2); // run with loader2
    }
    public static void changeT2(URL url) throws Exception{
        URL [] urls={url};
        URLClassLoader ucl = new URLClassLoader(urls);
        Class<?> c2 = ucl.loadClass("T2");
        Constructor<?> conc = c2.getConstructor();
        Object obj = conc.newInstance();
        Method m = c2.getMethod("change", int.class);
        m.invoke(obj, 0); // change T2.foo=0
    }
    public static void run(URL url) throws Exception{
        URL [] urls={url};
        URLClassLoader ucl = new URLClassLoader(urls);
        Class<?> c1 = ucl.loadClass("T1");
        Constructor<?> conc = c1.getConstructor();
        Object obj = conc.newInstance();
        Method m = c1.getMethod("xadd", int.class, int.class);
        m.invoke(obj, 0, 0);
    }
}