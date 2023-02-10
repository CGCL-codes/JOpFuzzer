import java.io.*;

public class Blah {

    static final int ITERATIONS = 60000;

    public static void main(String [] args) throws Exception {
        for (int x=0; x<10; x++) {
            test1(false);
            test2(false);
            test3(false);
        }
        test1(true);
        test2(true);
        test3(true);
    }

    volatile static long checksum = 0;

    private static void test1(boolean reportResult) throws Exception {
        FileInputStream fis = new FileInputStream("inputfile");
        FileOutputStream fos = new FileOutputStream("outputfile");

        DataInputStream dis = new DataInputStream(new BufferedInputStream(fis));
        DataOutputStream dos=new DataOutputStream(new BufferedOutputStream(fos));

        long check = 0;
        long startTime = System.currentTimeMillis();
        for (int x=0; x<ITERATIONS; x++) {
            short s = dis.readShort();
            dos.writeShort(s);
            check += s;
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        if (reportResult)
            reportResult("readShort", "milliseconds", totalTime);

        dis.close();
        dos.close();
        checksum = check;
        return;
    }

    private static void test2(boolean reportResult) throws Exception {
        FileInputStream fis = new FileInputStream("inputfile");
        FileOutputStream fos = new FileOutputStream("outputfile");

        DataInputStream dis = new DataInputStream(new BufferedInputStream(fis));
        DataOutputStream dos=new DataOutputStream(new BufferedOutputStream(fos));

        long check = 0;
        long startTime = System.currentTimeMillis();
        for (int x=0; x<ITERATIONS; x++) {
            int s = dis.readInt();
            s = dis.readInt();
            dos.writeInt(s);
            dos.writeInt(s);
            check += s;
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        if (reportResult)
            reportResult("readInt", "milliseconds", totalTime);

        dis.close();
        dos.close();
        checksum = check;
        return;
    }

    private static void test3(boolean reportResult) throws Exception {
        FileInputStream fis = new FileInputStream("inputfile");
        FileOutputStream fos = new FileOutputStream("outputfile");

        DataInputStream dis = new DataInputStream(new BufferedInputStream(fis));
        DataOutputStream dos=new DataOutputStream(new BufferedOutputStream(fos));

        long check = 0;
        long startTime = System.currentTimeMillis();
        for (int x=0; x<ITERATIONS; x++) {
            long s = dis.readLong(); s = dis.readLong();
            dos.writeLong(s); dos.writeLong(s);
            check += s;
        }
        dos.flush();

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        if (reportResult)
            reportResult("readLong", "milliseconds", totalTime);

        dis.close();
        dos.close();
        checksum = check;
        return;
    }

    private static void reportResult(String label, String unit, long result) {
        StringBuffer message = new StringBuffer(100);
        message.append(label + " completed in " + result + " " + unit);
        System.err.println(message);
    }
}