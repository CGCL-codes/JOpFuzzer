
package buggyJVM;

/**
 * This class hangs or crases the JVM under Linux (kernel 2.4.18)
 *
 * # java -version
 * java version "1.4.1"
 * Java(TM) 2 Runtime Environment, Standard Edition (build 1.4.1-b21)
 * Java HotSpot(TM) Client VM (build 1.4.1-b21, mixed mode)
 *
 * @author Márton Németh <###@###.###>
 */


public class buggyJVM {

        private class ListItem {
                ListItem        next;
                Object          data;
                int                     refCount;
        }

        static void test1() {
                ListItem[] objs = null;

                System.out.println("objs = " + objs);
                /**
                 * The next line will crash the JVM
                 */
                System.out.println("objs.length = " + objs.length);
        }

        static void test2() {
                Object[] objs = null;

                System.out.println("objs = " + objs);
                /**
                 * The next line will hang the JVM
                 */
                System.out.println("objs.length = " + objs.length);
        }


        public static void main(String args[]) {
                test1();
                test2();
        }

}

