
// Foo.java
class Foo {
  static Cloneable[] c;
  public static void main(String[] args) {
    c = new String[1][]; // legal, widening conversion
  }
}

