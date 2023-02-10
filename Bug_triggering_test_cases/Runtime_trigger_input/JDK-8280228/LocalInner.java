
public class LocalInner {
	private static int i;
	public void myMethod() {
		
		class Inner {
			private void method() {
				System.out.println("Hi harry!!" + i);
			}
		}
		Inner i = new Inner();
		i.method(); // this access should be invalid as I am out of the class defination
	}
}

class LocalTest{
	
	public static void main(String[] args) {
		LocalInner li =  new LocalInner();
		li.myMethod();
	}
}
