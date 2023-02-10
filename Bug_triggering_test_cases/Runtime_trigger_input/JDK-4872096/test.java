

public class Test {
    static class Oops {
        private String name;

        public Oops(String name) {
            this.name = name;
        }

        public String toString() {
            return "[group=" + this + name + "]";
        }
    }

    public static void main(String[] args) {
        Oops a = new Oops("foo");
        System.out.println("" + a);
    }
}
