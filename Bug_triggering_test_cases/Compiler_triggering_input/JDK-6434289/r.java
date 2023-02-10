import java.io.PrintStream;

public class r
{
    static int square(int i) {
        return i * i;
    }

    public static void main(String[] strings) {
        while (run(strings, System.out) == 0) {
        }
    }

    public static int run(String[] strings, PrintStream printstream) {
        int i = 3;
        int j = 0;
        try {
            i = i / 0 * (j = square(i - 2) + --i);
        } catch (ArithmeticException arithmeticexception) {
            if (i != 3 || j != 0) {
                printstream.print("* is tested: i = ");
                printstream.print(i);
                printstream.print("; j = ");
                printstream.println(j);
                return 2;
            }
        }
        try {
            i = i / 0 / (j = (i = 0) - square(i - 1));
        } catch (ArithmeticException arithmeticexception) {
            if (i != 3 || j != 0) {
                printstream.print("/ is tested: i = ");
                printstream.print(i);
                printstream.print("; j = ");
                printstream.println(j);
                return 2;
            }
        }
        try {
            i = i / 0 % (j = square(i + 2) - ++i);
        } catch (ArithmeticException arithmeticexception) {
            if (i != 3 || j != 0) {
                printstream.print("% is tested: i = ");
                printstream.print(i);
                printstream.print("; j = ");
                printstream.println(j);
                return 2;
            }
        }
        return 0;
    }
}