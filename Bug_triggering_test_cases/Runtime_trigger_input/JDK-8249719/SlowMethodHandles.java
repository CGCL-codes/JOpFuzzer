import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class SlowMethodHandles extends ClassLoader {

    // Generate a MethodHandle for ClassName.m()
    private MethodHandle generate(String className) throws ReflectiveOperationException {
        byte[] buf = new byte[100];
        int size = writeClass(buf, className);
        Class<?> cls = defineClass(null, buf, 0, size);
        return MethodHandles.publicLookup().findStatic(cls, "m", MethodType.methodType(void.class));
    }

    // Produce a class file with the given name and a single method:
    //     public static native void m();
    int writeClass(byte[] buf, String className) {
        return ByteBuffer.wrap(buf)
                .putInt(0xCAFEBABE)
                .putInt(50)
                .putShort((short) 7)
                .put((byte) 7).putShort((short) 2)
                .put((byte) 1).putShort((short) className.length()).put(className.getBytes())
                .put((byte) 7).putShort((short) 4)
                .put((byte) 1).putShort((short) 16).put("java/lang/Object".getBytes())
                .put((byte) 1).putShort((short) 1).put("m".getBytes())
                .put((byte) 1).putShort((short) 3).put("()V".getBytes())
                .putShort((short) 0x21)
                .putShort((short) 1)
                .putShort((short) 3)
                .putShort((short) 0)
                .putShort((short) 0)
                .putShort((short) 1)
                .putShort((short) 0x109)
                .putShort((short) 5)
                .putShort((short) 6)
                .putShort((short) 0)
                .putShort((short) 0)
                .position();
    }

    public static void main(String[] args) throws Exception {
        SlowMethodHandles generator = new SlowMethodHandles();
        List<MethodHandle> handles = new ArrayList<>();
        for (int i = 0; ; i++) {
            handles.add(generator.generate("MH$" + i));
            if (i % 1000 == 0) {
                System.out.println("Generated " + i + " handles");
            }
        }
    }
}
