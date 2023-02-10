/*
 * This example crashes the Solaris Sparc JVM for java 1.4.0 and 1.4.1_01
 * with a core dump. It works on 1.3.1_06 with -client and -server options.
 * The J2SE_Solaris_8_Recommended patches were all installed.
 * The crash happens with the java -server command line option and can be
avoided
 * by using the -client command line argument or by changing the while(true)
code
 * in the inc() method to be while(!done) and set done to false as shown in the
comments
 * in the code.
 *
 * run with java -server EntryAutoStart to show error
 * run with java -client EntryAutoStart to show working program
 *
 * The output when the program works (-client mode):
 *
 * Starting test
 * test(): 1
 * test(): 2
 * test(): 3
 * test(): 4
 * test(): 5
 * test(): 6
 * test(): 7
 * test(): 8
 * test(): 9
 * test(): 10
 *
 * The output when the JVM crashes (-server mode):
 *
 * Unexpected Signal : 11 occurred at PC=0xFE0D4040
 * Function=[Unknown. Nearest: JVM_ArrayCopy+0x4F5C]
 * Library=/home/jharris/java1.4.1_01/jre/lib/sparc/server/libjvm.so
 *
 * Current Java thread:
 *
 * Dynamic libraries:
 * 0x10000 ../java1.4.1_01/bin/java
 * 0xff350000 /lib/libthread.so.1
 * 0xff390000 /lib/libdl.so.1
 * 0xff200000 /lib/libc.so.1
 * 0xff330000 /usr/platform/SUNW,Ultra-5_10/lib/libc_psr.so.1
 * 0xfe000000 /home/jharris/java1.4.1_01/jre/lib/sparc/server/libjvm.so
 * 0xff2d0000 /lib/libCrun.so.1
 * 0xff1d0000 /lib/libsocket.so.1
 * 0xff100000 /lib/libnsl.so.1
 * 0xff0d0000 /lib/libm.so.1
 * 0xff300000 /lib/libw.so.1
 * 0xff0b0000 /lib/libmp.so.2
 * 0xff070000
/home/jharris/java1.4.1_01/jre/lib/sparc/native_threads/libhpi.so
 * 0xff040000 /home/jharris/java1.4.1_01/jre/lib/sparc/libverify.so
 * 0xfe7c0000 /home/jharris/java1.4.1_01/jre/lib/sparc/libjava.so
 * 0xff020000 /home/jharris/java1.4.1_01/jre/lib/sparc/libzip.so
 * 0xfdf90000 /usr/lib/locale/en_US.ISO8859-1/en_US.ISO8859-1.so.2
 *
 * Local Time = Thu Nov 14 14:11:15 2002
 * Elapsed Time = 1
 * #
 * # HotSpot Virtual Machine Error : 11
 * # Error ID : 4F530E43505002E6 01
 * # Please report this error at
 * # http://java.sun.com/cgi-bin/bugreport.cgi
 * #
 * # Java VM: Java HotSpot(TM) Server VM (1.4.1_01-b01 mixed mode)
 * #
 * # An error report file has been saved as hs_err_pid422.log.
 * # Please refer to the file for further information.
 * #
 * Abort (core dumped)
 */

public class EntryAutoStart
{
    private static volatile int reg = 0;
    private static volatile boolean go = false;


    public EntryAutoStart()
    {
        RunThread rt = new RunThread(this);
        rt.setDaemon(true);
        rt.start();
    }

    public int test()
    {
        go = true;

        while(go)
        {}

        return(reg);
    }

    public void inc()
    {
        // Swapping in the following two lines prevents the JVM
        // from crashing in -server mode.
        //
        // boolean done = false;
        // while(!done)

        while(true)
        {
            while(!go)
            {}

            reg++;

            go = false;
        }
    }


    public static void main(String[] args)
    {
        EntryAutoStart eas = new EntryAutoStart();

        System.out.println("Starting test");

        for(int i=0; i<10; i++)
            System.out.println("test(): " + eas.test());
    }
}

class RunThread extends Thread
{
    private EntryAutoStart eas;

    public RunThread(EntryAutoStart eas)
    {
        super();

        this.eas = eas;
    }

    public void run()
    {
        eas.inc();
    }
}