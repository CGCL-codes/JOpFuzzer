
package net.chandrat;

public class Cross {

	static class A {
		public final static B b = new B();

		public void randomA() {
			System.out.println("randomA...");
		}
	};

	static class B {
		public final static A a = new A();

		public void randomB() {
			System.out.println("randomB...");
		}
	}

	public static void main(String[] args) {
		System.out.println("starting...");
		System.out.println(Cross.class.getClassLoader());

		new Thread(new Runnable() {
			@Override
			public void run() {
				A.b.randomB();
			}
		}).start();

		B.a.randomA();
		System.out.println("ending...");
	}
}

