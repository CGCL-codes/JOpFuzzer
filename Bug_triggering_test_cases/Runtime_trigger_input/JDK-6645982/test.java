
public class Main {
    public static void main(String[] args) {
        System.out.println(new C());
    }
}

interface I {
    void i0();
    void i1();
}

abstract class A {
    void a0() {}
    abstract void i0();
}

abstract class B extends A implements I {
    void b0() {}
}

class C extends B {
    @Override public void i0() {}
    public void i1() {}
    void c0() {}
}

