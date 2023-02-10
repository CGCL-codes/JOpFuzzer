
public class Main {
    interface Intf {
        default void a() {
        }
        default void unusedButNeededToReproduceIssue() {
        }
    }
    static class B extends pkg.A implements Intf {
    }
    static class C extends B {
        public void a() {
        }
    }
    public static void main(String[] args) {
        ((B)new C()).a();
    }
}





