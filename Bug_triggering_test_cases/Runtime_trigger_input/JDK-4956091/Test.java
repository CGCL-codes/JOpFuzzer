
public class Test {
	int a;
	class DerivedClass extends BaseClass {
		public void doStuff() {
			a = 1;
		}
	}
	public Test() {
		new DerivedClass();
	}
	public static void main( String args[] ) {
		new Test();
	}
}
class BaseClass {
	public BaseClass() { doStuff();	}
	public void doStuff() {}
}
