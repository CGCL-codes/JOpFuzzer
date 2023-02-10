class Test {
    static Object someMethod() { throw new RuntimeException("initialization_error"); }
    static boolean out(String s) { System.out.println(s); return true; }

    // K interface with a default method has an initialization error
    interface K {
        static final Object CONST = someMethod();
        default int method() { return 2; }
    }

    // Iunlinked is not linked when K gets an initialization error. Linking Iunlinked should not
    // get NoClassDefFoundError because it does not depend on the initialization state of K for
    // linking. There's bug now where it does, filed separately.
    interface Iunlinked extends K {
        boolean v = out("Iunlinked");
    }

  static class C implements K {}

  public static void main(java.lang.String[] unused) {
      try {
          C c = new C();
      } catch (ExceptionInInitializerError e) {
          System.out.println("this is expected");
      }
      boolean b = Iunlinked.v; // should not fail!
  }
}