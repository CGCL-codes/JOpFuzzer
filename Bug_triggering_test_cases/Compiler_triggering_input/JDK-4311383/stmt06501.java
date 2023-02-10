import java.io.PrintStream;

public class stmt06501 {
  public static void main(String argv[]) {
     System.exit(run(argv, System.out) + 95/*STATUS_TEMP*/);
  }
  public static int run(String argv[],PrintStream out) {
     int a1 = 0;
     int a4 = 0;
     short b = 2;
   L:for ( int i = 1; i < 3; i++ ) {
        a1 += 1;
        do {
           b++;
           if ( b == 4 )
              break L;
        }
        while ( b < 5 );
        a4 += 1;
     }
     if ( a1 == 1 && a4 == 0 )
        return 0/*STATUS_PASSED*/;
     out.println ("failed");
     out.println( a1+" "+a4);
     return 2/*STATUS_FAILED*/;
  }
}