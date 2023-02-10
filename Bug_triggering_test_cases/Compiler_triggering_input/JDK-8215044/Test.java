public class Test {
    public static void main(String[] args) {
        Test issue = new Test();
        for (int i = 0; i < 10000; i++) {
            issue.test(new int[999]);
        }
    }
    public void test (int[] iaarg) {
        int[] iarr = new int[777];
        for (int i = 4; i > 0; i--) {
            for (int j = 0; j <= i - 1; j++) {
                int istep = 2 * j - i + 1;
                int iadj = 0;
                if (istep < 0) {
                    iadj = iarr[0-istep] + iaarg[i-1];
                } else {
                    iadj = iarr[istep] + iaarg[i-1];
                }
            }
        }
    }
}

