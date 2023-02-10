public class TestInterface {
    public static void main(String[] args) {
        MyInterface i = new MyInterface() {
            public void foo() {
                System.out.println("foo");
            }
        };

        i.foo();
    }
}
