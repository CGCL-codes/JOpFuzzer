class TestLoop {
    private int char_var;
    private int long_var;
    private int else_var;
    private int do_var;
    private int case_var;
    private int int_var;
    private boolean e;

    public TestLoop() {
        char_var = 4;
        long_var = char_var * 4;
        else_var = 0;
        int_var = 8;
        do_var = 0;
        case_var = 0;
        e = true;
    }

    private int crash_method() {
        byte[] bytearray = new byte[20];
        int var0 = 1;

        do {
            int var1 = 0;

            for (int count = var0; count < long_var; count++) {
                if (bytearray[count] == else_var) {
                    var1 = count;
                    break;
                }
            }

            if (var1 == 0)
                break;
            var0 = var1 + 1;

            int var2 = var1 % char_var + 1;

            do {
                if ((e || var2 != 1) && var2 <= char_var - 2) {
                    if (bytearray[var1 + 1] == case_var && bytearray[var1 + char_var] == do_var) {
                        int var3;

                        for (var3 = var2 + 2; var3 < char_var + 1; var3++) {
                            if (bytearray[var1 + var3 - var2] == int_var) {
                                break;
                            }
                        }
                    }
                }
            } while (false);
        } while (var0 < long_var - (char_var * 2 + 1));
        return -1;
    }

    public static void main(String[] args) {
        TestLoop tl = new TestLoop();

        for (int count = 0; count < 10005; count++) {
            tl.crash_method();
        }
    }
}
