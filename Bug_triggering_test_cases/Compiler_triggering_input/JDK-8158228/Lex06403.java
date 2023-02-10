public class Lex06403 {
 
	public static void main( String args[] ) {
		System.exit( run(args) + 95/*STATUS_TEMP*/ ); 
	}
 
	public static int run( String args[] ) {
	
		String s;
		char c = '\47';
		
		s = "next \477 octal escape sequence is valid";
		if ( s.charAt(5) == c && s.charAt(6) == '7' ) {
                        System.out.println("PASSED");
			return 0/*STATUS_PASSED*/;
                }
		System.out.println("failed: " + 
			Integer.toOctalString(s.charAt(5)) + " " + 
			Integer.toOctalString(s.charAt(6)));
		return 2/*STATUS_FAILED*/;
	}
 
}
