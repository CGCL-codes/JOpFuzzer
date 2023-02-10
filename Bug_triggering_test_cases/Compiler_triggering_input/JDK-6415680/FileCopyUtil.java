import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

public class FileCopyUtil {

    public FileCopyUtil() {

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        File src = new File(args[0]);

        try {
            byte[] file = new byte[(int) src.length()];
            FileChannel channel = new FileInputStream(src).getChannel();
            MappedByteBuffer buffer = channel.map(MapMode.READ_ONLY, 0, src.length());
            buffer.get(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}