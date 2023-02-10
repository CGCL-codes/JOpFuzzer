
public class Arithmetic_Exception {
public static void main(String[] args) {
	int x=5;
	int y=0;
	try {System.out.println(x/y);}
	catch(ArithmeticException A) {System.out.println("y cannot be 0");}
}
}
