public class test{
public static void main(String[] args) {
		String a = new String("1") + new String("2");
		a.intern();
		a.intern();
		String b = "12";
		System.out.println(a == b);

	}
}