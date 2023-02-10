
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;

public class HSERRTest {

	public static void main(String[] args) throws IOException, InterruptedException {
		File file = File.createTempFile("test_hs_err", ".txt");

		StringBuilder s = new StringBuilder();
		for (int i = 1; i < 7000; i++) {
			s.append("1");
		}
		Files.write(file.toPath(), s.toString().getBytes());

		FileChannel fileChannel = new RandomAccessFile(file, "r").getChannel();
		MappedByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());

		Files.write(file.toPath(), "2".getBytes());

		buffer.get(new byte[4096]);
		buffer.get(new byte[8]);//but with "buffer.get(new byte[6]);", it will just fail with java.lang.InternalError.
	}
}
