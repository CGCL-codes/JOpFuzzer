import java.io.*;
import java.util.*;

public class LoadClassOOM2 {
    static class Link {
        Link next;
    }

    static Link head;

    static int exhaustHeap(int num, Vector classes) throws Throwable {
        int n = 0;

        System.gc();
        System.gc();

        for (;;) {
            try {
                Link link = new Link();
                n++;
                link.next = head;
                head = link;
            } catch (Throwable t) {
                break;
            }
        }

        // Give back some memory so we can println
        for (int i=0; i<num; i++) {
            if (head != null) {
                head = head.next;
                n--;
            } else {
                break;
            }
        }
        System.gc();
        System.gc();
        System.out.println("total  = " + Runtime.getRuntime().totalMemory());
        System.out.println("free   = " + Runtime.getRuntime().freeMemory());
        System.out.println("blocks = " + n);
        System.gc();
        System.gc();

        int loaded = 0;
        String className = null;

        // number of retries on the current class
        int retries = 0;

        // number of classes that have large number of retries;
        final int MANY_RETRIES = 6;
        int num_classes_with_retries = 0;
        int num_classes_with_many_retries = 0;

        for (int i=800; i<classes.size(); i++) {
            className = (String)classes.elementAt(i);
            try {
                Class.forName(className);
                ++ loaded;
                retries = 0;
                className = null;
            } catch (OutOfMemoryError oom) {
                if (head != null) {
                    head = head.next;
                    num ++;
                    n--;

                    try {
                        System.out.print(className);
                        System.out.print(" : ");
                        System.out.print("num = " + num);
                    } catch (Throwable ttt) {;}
                    try {
                        System.out.println("");
                    } catch (Throwable ttt) {;}

                    i--; // retry
                    retries ++;

                    if (retries == 1) {
                        num_classes_with_retries ++;
                    } else if (retries == MANY_RETRIES) {
                        num_classes_with_many_retries ++;
                        if (num_classes_with_many_retries >= 2) {
                            // We have tested enough!
                            break;
                        }
                    }
                } else {
                    System.gc();
                    oom.printStackTrace();
                    break;
                }
            } catch (Throwable t) {
                continue;
            }
        }

        head = null;
        System.gc();
        System.out.println("Loaded " + loaded + " classes");
        System.out.println(num_classes_with_retries + " classes have been retried");
        System.out.println(num_classes_with_many_retries + " classes have been retried " + MANY_RETRIES + " times or more");

        System.out.println("total = " + Runtime.getRuntime().totalMemory());
        System.out.println("free  = " + Runtime.getRuntime().freeMemory());
        System.gc();
        System.gc();

        if (className != null) {
            try {
                Class.forName(className);
                System.out.println("Successfully loaded " + className + " after freeing heap space");
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }

        return n;
    }

    static Vector getCandidates()  {
        String file = System.getProperty("java.home") + "/lib/classlist";
        Vector v = new Vector();

        System.out.println("Loading classes specified in " + file);

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = null;

            while ((line = br.readLine()) != null) { // while loop begins here
                if (line.startsWith("#")) {
                    continue;
                }
                line = line.replace('/', '.');
                v.addElement(line);
            }
            br.close();
        } catch (Throwable t) {
            t.printStackTrace();
            // FIXME
        }

        return v;
    }

    public static void main(String args[]) throws Throwable {
        int num = 1620;

        try {
            num = Integer.parseInt(args[0]);
        } catch (Throwable t) {;}

        //Class.forName("java.util.Queue");
        //Class.forName("sun.misc.Cleaner");

        System.out.println("Please run with small amount of heap, and disable JIT, like:\n    -Xms16m -Xmx16m -Xint");
        System.out.println("ignore ======v");
        (new Throwable()).printStackTrace();
        System.out.println("ignore ======^");

        // Try to exhaust the heap
        try {
            Vector v = getCandidates();
            int n = exhaustHeap(num, v);
            System.out.print(n);
            System.out.println();
        } catch (Throwable x) {
            head = null;
            System.gc();
            System.out.println("......");
            x.printStackTrace();
        }
    }
}