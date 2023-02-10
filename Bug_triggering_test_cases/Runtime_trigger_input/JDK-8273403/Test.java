
import jdk.internal.org.objectweb.asm.ClassWriter;

public class Test {

    public static void main(String[] var1) throws Throwable {

        for (int i = 0; i < 200000; i++){

            try {
                String name = "UM:}<u";
                ClassWriter classWriter = new ClassWriter(0);
                classWriter.visit(i, 1, name, (String)null, "java/lang/Object", (String[])null);
                makeClassBytesLoader(classWriter.toByteArray(), name).loadClass(name);

                System.err.println("Version: " + i + " " + "Test Pass");
            } catch (Error e){

                if (e.toString().contains("UnsupportedClassVersionError")){
//                    System.err.println("Version: " + i + " " + "UnsupportedClassVersionError");
                } else if (e.toString().contains("ClassFormatError")){
                    System.err.println("Version: " + i + " " + "ClassFormatError");
                }
            }
        }
    }

    public static ClassLoader makeClassBytesLoader(final byte[] classBytes, final String className) {
        return new ClassLoader() {
            @Override
            protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {

                if (!name.equals(className))
                    return super.loadClass(name, resolve);
                return defineClass(className, classBytes, 0, classBytes.length);
            }
        };
    }
}
