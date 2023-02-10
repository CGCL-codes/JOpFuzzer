import org.apache.commons.lang.StringUtils;

public class CompactStringBug {

    protected static final int MAX_FAILURES = 5;

    public static void main(String[] args) {
        final String id = "123456";
        final String expectedReversedId = "654321";

        /*
          The bug does not reproduce if you include a multibyte character in the string
        */
// final String id = "12345\u1234";
// final String expectedReversedId = "\u123454321";

        System.out.println("Original string: " + id);
        System.out.println("Expected reversed string: " + expectedReversedId);
        System.out.println();

        int failures = 0;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            String reversedId = StringUtils.reverse(id);
            if (!expectedReversedId.equals(reversedId)) {
                failures++;
                System.out.println("Iteration: " + i);
                System.out.println("Actual: " + reversedId);
                System.out.println();
                if (failures == MAX_FAILURES) {
                    System.out.println("Exiting after " + MAX_FAILURES + " failures");
                    System.exit(1);
                }
            }
        }
        System.out.println("Completed normally. Exiting.");
    }
} 