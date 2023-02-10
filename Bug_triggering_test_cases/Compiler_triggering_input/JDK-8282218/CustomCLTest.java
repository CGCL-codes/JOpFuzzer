import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Arrays;

public class CustomCLTest {

    static class CL extends ClassLoader {
        private Field f;

        public CL() throws ReflectiveOperationException {
            f = loadClass(Test.class.getName()).getField("COUNTER");
        }

        @Override
        protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            if (name.startsWith("CustomCLTest$Test")) {
                System.out.println("TRACE: " + name);

                Class<?> c = findLoadedClass(name);
                if (c != null) {
                    return c;
                }
                try {
                    if (f != null) {
                        f.set(null, (int) f.get(null) + 1);
                    }
                    InputStream in = getSystemResourceAsStream(name.replace('.', File.separatorChar) + ".class");
                    byte[] buf = in.readAllBytes();
                    return defineClass(name, buf, 0, buf.length);
                } catch (IOException | IllegalAccessException e) {
                    throw new ClassNotFoundException(name);
                }
            }
            return super.loadClass(name, resolve);
        }
    }

    public static class Test {
        public static int COUNTER = 0;

        static class T {}

        public static Object[] run(boolean b) {
            int t1 = COUNTER;
            Class<?> c = null;
            if (b) {
                c = T.class;
            }
            int t2 = COUNTER;
            return new Object[] { c, t2 - t1};
        }
    }

    public static void main(String[] args) throws ReflectiveOperationException {
        ClassLoader cl = new CL();
        Class<?> c = Class.forName(Test.class.getName(), true, cl);

        Field f = c.getDeclaredField("COUNTER");

        System.out.println(f.get(null));

        {
            Object[] r = (Object[]) c.getDeclaredMethod("run", boolean.class).invoke(null, false);
            System.out.println(Arrays.deepToString(r));
        }
        {
            Object[] r = (Object[]) c.getDeclaredMethod("run", boolean.class).invoke(null, true);
            System.out.println(Arrays.deepToString(r));
        }

        System.out.println(f.get(null));
    }
}
