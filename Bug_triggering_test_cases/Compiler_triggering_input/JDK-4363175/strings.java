public class strings {

    static String detab_String(String s) {
            if (s.indexOf('\t') == -1)
                    return s;
            String res = "";;
            int len = s.length(), pos = 0, i = 0;
            for (; i < len && s.charAt(i) == '\t'; i++) {
                    res += " ";
                    pos += 8;
            }
            for (; i < len; i++) {
                    char c = s.charAt(i);
                    if (c == '\t') {
                            do {
                                    res += " ";
                                    pos++;
                            } while (pos % 8 != 0);
                    }
                    else {
                            res += c;
                            pos++;
                    }
            }
            return res;
    }
            
    static String detab_StringBuffer(String s) {
            if (s.indexOf('\t') == -1)
                    return s;
            StringBuffer sb = new StringBuffer();
            int len = s.length(), pos = 0, i = 0;
            for (; i < len && s.charAt(i) == '\t'; i++) {
                    sb.append(" ");
                    pos += 8;
            }
            for (; i < len; i++) {
                    char c = s.charAt(i);
                    if (c == '\t') {
                            do {
                                    sb.append(' ');
                                    pos++;
                            } while (pos % 8 != 0);
                    }
                    else {
                            sb.append(c);
                            pos++;
                    }
            }
            return sb.toString();
    }
            
    static String testlist[] = {
            "",
            "\t",
            "\t\t\tabc",
            "abc\tdef",
            "1234567\t8",
            "12345678\t9",
            "123456789\t"
    };
            
    public static void main(String args[]) {
            System.out.println("Comparison of String vs. StringBuffer...");
            for (int i = 0; i < testlist.length; i++) {
                    String tc = testlist[i];
                    if (!detab_String(tc).equals(detab_StringBuffer(tc)))
                            System.err.println(tc);
            }
            
            String test_string = "\t\tthis is a test\tof detabbing performance";
            int N = 1000;
            
            System.out.print("" + N + " Strings");
            for (int i = 0; i < N; i++) {
                    if ((i % 100) == 0)
                            System.out.print('.');
                    detab_String(test_string);
            }
            
            System.out.print("\n" + N + " StringBuffers");
            for (int i = 0; i < N; i++) {
                    if ((i % 100) == 0)
                            System.out.print('.');
                    detab_StringBuffer(test_string);
            }
            System.out.println("\nFinished");
    }
    }