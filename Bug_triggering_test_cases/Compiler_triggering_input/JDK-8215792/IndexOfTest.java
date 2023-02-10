public class IndexOfTest {
    public static final String sourceString = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata ";
    public static final String constantString = sourceString.substring(50, 50 + 64);

    public static final StringBuilder sb;
    static {
        sb = new StringBuilder(sourceString);
        sb.append(constantString);
        sb.setLength(sourceString.length());
    }

    public static void main(String[] argv) {
        for(int i = 0; i < 2000; i++) {
            testStringBuilderIndexOfConstantOffset();
        }
        System.out.println("Test was successful");
    }

    public static void testStringBuilderIndexOfConstantOffset() {
        int off = testStringBuilderIndexOfOffset(sb, constantString, Math.max(0, sourceString.length() - constantString.length()));
        if(off != -1) {
            System.out.println("ERROR: Expected offset -1, got " + off);
            System.exit(1);
        }
    }

    public static int testStringBuilderIndexOfOffset(StringBuilder a, String b, int fromIndex) {
        return a.indexOf(b, fromIndex);
    }
}