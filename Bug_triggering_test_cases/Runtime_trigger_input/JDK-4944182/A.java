
// File A.java

public class A {

	public static void main(String[] aaa) {
		new A().test(new B());
	}

	public void test(B b) {
		b.test(this);
	}
}

class B {

	public void test(A a) {
		a.test(this);
	}
}
