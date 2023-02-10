
public class DefaultVsStaticInterfaceMethodTest {
    public interface A {
        default void m() {
            System.out.println("A.m() called");
        }
    }

    public interface B {
        static void m() {
            System.out.println("B.m() called");
        }
    }

    public interface C extends B, A {
    }

    public static void main(String[] args) {
        C c = new C() {};
        c.m();
    }
}

