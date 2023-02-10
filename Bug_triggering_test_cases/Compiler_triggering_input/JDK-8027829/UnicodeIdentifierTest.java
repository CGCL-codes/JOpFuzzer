public class UnicodeIdentifierTest {
    public static void main(String args[]) {
        System.out.println("Can I use \\u0001 in identifier name? " +
                           (Character.isJavaIdentifierPart(1) ? "yes" : "no"));
	for (int i = 0; i < 100000; i++ )
        methodWithUnicode\u0001Char();
    }
    public static int a = 0;
    public static void methodWithUnicode\u0001Char() {
        a++;
    }
}
