import java.awt.Component;
import java.lang.reflect.Field;

import static jdk.internal.org.objectweb.asm.Opcodes.*;
import jdk.internal.org.objectweb.asm.*;
import sun.misc.Unsafe;

public class PrimitiveHostClass {

    static final Unsafe U;
    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            U = (Unsafe) theUnsafe.get(null);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    public static void testVMAnonymousClass(Class<?> hostClass) {

        // choose a class name in the same package as the host class
        String prefix = packageName(hostClass);
        if (prefix.length() > 0)
            prefix = prefix.replace('.', '/') + "/";
        String className = prefix + "Anon";

        // create the class
        String superName = "java/lang/Object";
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS
                                         + ClassWriter.COMPUTE_FRAMES);
        cw.visit(V1_8, ACC_PUBLIC + ACC_FINAL + ACC_SUPER,
                 className, null, superName, null);
        byte[] classBytes = cw.toByteArray();
        int cpPoolSize = constantPoolSize(classBytes);
        Class<?> anonClass
            = U.defineAnonymousClass(hostClass, classBytes, new Object[cpPoolSize]);
    }

    private static String packageName(Class<?> c) {
        if (c.isArray()) {
            return packageName(c.getComponentType());
        } else {
            String name = c.getName();
            int dot = name.lastIndexOf('.');
            if (dot == -1) return "";
            return name.substring(0, dot);
                    }
    }

    private static int constantPoolSize(byte[] classFile) {
        return ((classFile[8] & 0xFF) << 8) | (classFile[9] & 0xFF);
    }

    public static void main(String args[]) {
          testVMAnonymousClass(PrimitiveHostClass.class);
          testVMAnonymousClass(int.class);
    }
}

