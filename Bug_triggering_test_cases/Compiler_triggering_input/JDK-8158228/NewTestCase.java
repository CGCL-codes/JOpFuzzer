
import java.text.StringCharacterIterator;

/*
 * Copyright (c) 2013 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 */
/**
 * Created: May 31, 2016
 * @author skovalev
 */
public class NewTestCase {
    public static void main(String arg[]) {
        StringCharacterIterator0010();
    }

    public static void StringCharacterIterator0010() {
        boolean passed = true;
        String text = "some string for testing";
        int begin = 3;
        int end = text.length() - 2;
        int index = (int) ((begin + end) / 2);
        StringCharacterIterator iter = new StringCharacterIterator(text, begin,
                end, end);
        char c = iter.setIndex(index); // call method under test
        int expectedIndexes[] = { begin, end, index };
         if (!(c == text.charAt(index))) {
            System.err.println("method setIndex(int) return incorrect char");
            System.err.println("  returned char is " + c);
            System.err.println("  expected char is " + text.charAt(index));
            passed = false;
        }
        System.out.println(passed ? "OKAY" : "public void setIndex(int index)");
    }
}
