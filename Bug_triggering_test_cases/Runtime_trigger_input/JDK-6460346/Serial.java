
Try compiling this bit of code. Then run it on a 64-bit JVM with lots of heap.

java -Xmx10g -Xms10g Serial 185000000

import java.io.OutputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

class MyOutputStream extends OutputStream {
        public MyOutputStream() {}

        public void close() {}
        public void flush() {}
        public void write(byte[] b) {}
        public void write(byte[] b, int off, int len) {}
        public void write(int b) {}
}

public class Serial extends Thread {
        public static void main(String[] args) throws Exception {
                int length = Integer.parseInt(args[0]);
                MyOutputStream mos = new MyOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(mos);

                Vector vector = new Vector(15);
                System.out.println("CREATING VECTOR");
                for (int i=0; i<length; i++) {
                        if (i%10000000==0)
                                System.out.println(i);
                        vector.add(new Integer(i));
                }
                System.out.println("CREATED VECTOR");
                long startTime = System.currentTimeMillis();
                System.out.println("Beginning serialization");
                oos.writeObject(vector);
                long endTime = System.currentTimeMillis();
                System.out.println("Elapsed time = " + (endTime-startTime) + " msecs");
        }
}


