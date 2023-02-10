import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Random;

public class CorruptFinalLong {

    private static final Random random = new Random();

    private static void createFileIfNeeded(File file) throws IOException {
        if (!file.exists()) {
            int bufSize = 1000000;
            int rounds = 50;
            System.out.println("writing random file " + file + " of size " + (bufSize * rounds)
                    + " bytes");
            byte[] buf = new byte[bufSize];
            OutputStream out = new BufferedOutputStream(new FileOutputStream(file), 65536);
            for (int i = 0; i < rounds; i++) {
                random.nextBytes(buf);
                out.write(buf);
            }
            out.close();
        }
    }

    public static void main(String[] args) throws Exception {

        System.setErr(System.out);

        String[] props = new String[] { "java.vm.version", "java.vm.name", "sun.os.patch.level",
                "java.runtime.version", "java.vm.version", "os.arch", "os.name",
                "java.specification.version", "java.vm.specification.version",
                "java.specification.vendor", "java.version" };

        for (String p : props) {
            System.out.printf("%30s: %s", p, System.getProperty(p));
            System.out.println();
        }
        System.out.println();

        final File file = new File("random.dat");

        createFileIfNeeded(file);

        final Thread t = Thread.currentThread();

        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println("\tabout to interrupt thread '" + t + "' from thread '"
                            + Thread.currentThread() + "'");
                    t.interrupt();
                } catch (InterruptedException e) {
                    System.out.println("\tinterrupted in thread");
                    e.printStackTrace();
                }

            }
        }).start();

        final long start = System.currentTimeMillis();
        System.out.println("started at " + new Date(start) + " (" + start + " ms)");
        System.out.println();

        MessageDigest md = MessageDigest.getInstance("MD5");

        ByteChannel channel = new FileInputStream(file).getChannel();
        try {
            int cap = 10;
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(cap);
            byte[] local = new byte[cap];
            long lastRead = 0;
            boolean eof = false;
            while (!eof) {
                byteBuffer.rewind();
                int bytesReadThisTime = channel.read(byteBuffer);
                if (bytesReadThisTime == -1) {
                    eof = true;
                } else {
// totalRead += bytesReadThisTime;
                    byteBuffer.rewind();
                    byteBuffer.get(local, 0, bytesReadThisTime);
                    md.update(local, 0, bytesReadThisTime);
                    if (System.currentTimeMillis() - lastRead > 1000) {
                        lastRead = System.currentTimeMillis();
                    }
                }
            }
        } catch (Exception e) {
            if (Thread.interrupted()) {
                System.out.println("\t********* IO was interrupted in thread '"
                        + Thread.currentThread() + "': " + e);
            } else {
                System.out.println("\texception: " + e);
            }
        } finally {
            channel.close();
        }

        System.out.println();
        System.out
                .println("the following line should be identical to the similar one above, since variable 'start' is final:");
        System.out.println("started at " + new Date(start) + " (" + start + " ms)");

    }

}
