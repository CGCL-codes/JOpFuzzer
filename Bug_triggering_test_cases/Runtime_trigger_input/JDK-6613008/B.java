
public class B {
	static int nb;
	A va;
	B(){
		++nb;
		try {
			va = new A();
		} catch (Exception e) {
			System.out.println("B "+nb);
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
		B b=new B();
	}
}
public class A {
	B vb;
	static int na;
	A(){
		++na;
		try {
			vb=new B();
		} catch (RuntimeException e) {
			System.out.println("A "+na);
			e.printStackTrace();
		}
	}
}

Then java -Xss250m B crashes.
