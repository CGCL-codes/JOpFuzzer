// $JAVA_HOME/bin/java_g -server -Xbatch -Xcomp -XX:+PrintCompilation -XX:CompileOnly=.test StringTest2
// Correctly running program will exit silently.
// Incorrect program SEGV's and gives "implicit exception happened..." message.

import java.util.*;

public class StringTest2 {
    static final int LEN = 100;
    static final int LAST = LEN-1;

    static void test(String[] myArray) {
        int i;
        for (i=0; i<LAST; i++) {
            if (myArray[i].compareTo(myArray[LAST]) != 0) {
                System.out.println("D'oh!");
            }
        }
    }

    public static void main(String[] args) {
        String[] myArray = new String[LEN];
        String s = "foo";

        for (int j=0; j<LAST; j++) {
            if (j < LEN / 2) {
                myArray[j] = s;
            } else {
                myArray[j] = null;
            }
        }
        myArray[LAST] = s;

        try {
            test(myArray);
        } catch (NullPointerException e) {
        }
    }
}
