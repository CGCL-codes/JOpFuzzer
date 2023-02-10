import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Type;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.objectweb.asm.Opcodes.*;

public class CompilerCrash {

  public static void main(String[] args) throws Exception {
    var cw = new ClassWriter(0);
    cw.visit(V10, ACC_PUBLIC, "BadClass", null, "java/lang/Object", null);
    var mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "crash", "()V", null, null);
    mv.visitCode();
    mv.visitLdcInsn(
        new Handle(
            H_INVOKESTATIC,
            "java/lang/Class",
            "cast",
            "(Ljava/lang/Object;)Ljava/lang/Object;",
            false));
    mv.visitLdcInsn(Type.getType(String.class));
    mv.visitLdcInsn("some object");
    mv.visitMethodInsn(
        INVOKEVIRTUAL,
        "java/lang/invoke/MethodHandle",
        "invoke",
        "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;",
        false);
    mv.visitInsn(RETURN);
    mv.visitMaxs(3, 0);
    byte[] classBytes = cw.toByteArray();
    var loader = new BytesClassLoader("BadClass", classBytes);
    var method = Class.forName("BadClass", true, loader).getMethod("crash");
    for (int i = 0; i < 16000; i++)
      try {
        method.invoke(null);
      } catch (InvocationTargetException ignored) {
      }
  }

  private static final class BytesClassLoader extends ClassLoader {

    private final String name;
    private final byte[] bytes;

    BytesClassLoader(String name, byte[] bytes) {
      this.name = name;
      this.bytes = bytes;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
      if (!this.name.equals(name)) {
        throw new ClassNotFoundException(name);
      }
      byte[] bytes = this.bytes;
      return defineClass(name, bytes, 0, bytes.length);
    }
  }
}