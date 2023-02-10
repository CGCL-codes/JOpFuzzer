
public class Test {
	interface Demo {
		void demo(Object ... parameters);
	}

	public static void main(String[] args) {
		Demo demo = new Demo() {
			public void demo(Object ... parameters) {
				for (int i = 0; i < parameters.length; i++) {
					System.out.println(parameters[i]);
				}
			}
		};

		demo.demo("this", "is", "a", "demo");
	}
}
