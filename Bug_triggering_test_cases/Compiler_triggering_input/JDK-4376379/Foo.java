import java.lang.reflect.*;
  import sun.misc.Unsafe;

  public class Foo {
      static int i = 0;

      private void func() {
          i++;
      }

      public static void main(String[] args) throws Exception {
          int reps = Integer.parseInt(args[0]);
          Unsafe unsafe = Unsafe.getUnsafe();
          Method meth = Foo.class.getDeclaredMethod("func", new Class[0]);
          int key = unsafe.invocationKey(meth);
          Foo foo = new Foo();
          Object[] iargs = new Object[0];
          for (int i = 0; i < reps; i++) {
              unsafe.invokeSpecial(Foo.class, key, foo, iargs);
          }
      }
  }