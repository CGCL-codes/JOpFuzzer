import java.io.PrintStream;

public class b4257171 {

    public static void main(String argv[]) {
System.exit(run(argv, System.out) + 95/*STATUS_TEMP*/);
    }

    public static int run(String argv[], PrintStream out) {
int a1[] = {1, 2, 3};
int a2[] = {4, 5, 6};
try {
System.arraycopy(a1, Integer.MAX_VALUE, a2, 0, 3);
} catch (ArrayIndexOutOfBoundsException e) {
return 0/*STATUS_PASSED*/;
}
return 2/*STATUS_FAILED*/;
    }

}