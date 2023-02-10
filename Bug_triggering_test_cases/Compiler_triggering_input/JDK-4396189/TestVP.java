/**
 * 2000/11/07
 * Test case to debug Hotspot compiler bug
 *
 * To run:
 * Using regular JAVA:
 * java -XX:MaxInlineSize=6 -Xcomp -XX:CompileThreshold=5 TestVP
 * Using optimized build:
 * java -XX:MaxInlineSize=6 -Xcomp -XX:CompileThreshold=5 -XX:CompileOnly=TestVP.\<init\> TestVP
 *
 * <IMPORTANT>: The testcase fails when String.length (5 bcs) is being inlined.
 *
 * TestVP constructor is very sensitive. Addition of printlns
 * make problem disappear (String.length won't get inlined).
 */

class StringUtils
{
        public static String[] dumbSplitString()
        {
                        String[] mElements = new String [3];
                        mElements[0]="";
                        mElements[1]="hi";
                        mElements[2]="";
                        return mElements;
        }
}

public class TestVP
{
        //-------------------------------------
        // FIELDS
        //-------------------------------------

        /** Set of pathname elements */
        String[] mElements;

        /** offset into mElements at which entry's list of pathnames start */
        int mStart;

        /** number of elements beginning at mElements[mStart] */
        int mLength;

        //-------------------------------------
        // CONSTRUCTOR
        //-------------------------------------
        public TestVP () {
                        mElements = StringUtils.dumbSplitString();
                        mStart = 0;
                        mLength = mElements.length;
                        
                        while (mStart < mLength && mElements[mStart].length() == 0) {
                                mStart++;
                                mLength--;
                        }
                        while (mLength > 0 && mElements[mStart + mLength - 1].length() == 0) {
                                mLength--;
                        }
                        //int length = mElements[mStart].length();
        }

        //-------------------------------------
        //-------------------------------------
        public static void main (String args[]) {
                System.out.println ("\nStart test\n");
                for (int i=1; i<=10; i++)
                {
                    System.out.println ("Loop: " + i);
                        TestVP VP = new TestVP ();
                }
        }
}