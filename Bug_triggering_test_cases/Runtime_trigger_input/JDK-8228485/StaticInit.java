class StaticInit {
    static {
        if (true)
          throw new RuntimeException();
    }

    static Object get() {
        return new Object();
    }
}
