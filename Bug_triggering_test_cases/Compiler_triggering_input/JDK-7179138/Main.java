public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Java Version: " + System.getProperty("java.vm.version"));
        long[] durations = new long[60];
        for (int i = 0; true; i++) {
            // this empty for-loop is required to reproduce this bug
            for (long duration : durations) {
                // do nothing
            }
            {
                String s = "test";
                int len = s.length();

                s = s + s;
                len = len + len;

                s = s + s;
                len = len + len;

                s = s + s;
                len = len + len;

                if (s.length() != len) {
                    System.out.println("Failed at iteration: " + i);
                    System.out.println("Length mismatch: " + s.length() + " <> " + len);
                    System.out.println("Expected: \"" + "test" + "test" + "test" + "test" + "test" + "test" + "test" + "test" + "\"");
                    System.out.println("Actual: \"" + s + "\"");
                    System.exit(0);
                }
            }
        }
    }
}