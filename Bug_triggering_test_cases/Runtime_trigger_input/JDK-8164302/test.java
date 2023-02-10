
public class BadInterfaceCycle {

  interface Base {
    static final Object BASE_CONST = new Child() {
      {
        System.out.println("base clinit");
      }
    }.childDefaultMethod();

    default void baseDefaultMethod() {
    }
  }

  interface Child extends Base {
    static final Object CHILD_CONST = new Object() {
      {
        System.out.println("child clinit");
      }
    };

    static void childStaticMethod() {
      System.out.println("child static method");
    }

    default Object childDefaultMethod() {
      System.out.println("child default method");
      return null;
    }
  }

  public static void main(String[] args) {
    Child.childStaticMethod();
  }
}

