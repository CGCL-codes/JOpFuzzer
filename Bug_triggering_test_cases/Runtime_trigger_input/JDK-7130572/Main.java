
class A {
    static boolean x = B.b;
    static boolean a = true;
}
class B {
    static boolean b = true;
    static { if (A.a) throw new Error();  }
}
class Main {
    public static void main(String[] args) {
        Thread t1 = new Thread() {
                public void run() {
                    new A();
                }
            };
        Thread t2 = new Thread() {
                public void run() {
                    new B();
                }
            };
        t1.start();
        t2.start();
    }
}
