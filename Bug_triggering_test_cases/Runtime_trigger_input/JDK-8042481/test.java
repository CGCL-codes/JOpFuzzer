
package org.msn.org.msn.test.db.files;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class DataWriter implements AutoCloseable {

	private final AsynchronousFileChannel file;

	private final FileLock lock;

	private volatile long size;

	public DataWriter(Path filePath) throws IOException {

		file = AsynchronousFileChannel.open(filePath,
				StandardOpenOption.CREATE, StandardOpenOption.WRITE);

		lock = file.tryLock();

		size = file.size();
	}

	public long appendData(byte[] data) throws InterruptedException,
			ExecutionException, TimeoutException {

		long resultID = -1;

		long oldSize = size;

		size += data.length;

		if (writeToFile(data, oldSize)) {

			resultID = oldSize;
		}

		return resultID;
	}

	private boolean writeToFile(byte[] data, long position)
			throws InterruptedException, ExecutionException, TimeoutException {

		ByteBuffer buffer = ByteBuffer.allocate(data.length);
		buffer.clear();
		buffer.put(data);
		buffer.rewind();

		Future<Integer> future = file.write(buffer, position);

		int bytesWriten = future.get(Long.MAX_VALUE, TimeUnit.MILLISECONDS);

		if (bytesWriten < data.length) {

			return false;
		}

		return true;
	}

	public void close() throws IOException {

		lock.release();

		file.close();
	}
	
	public static void main(String[] args) {

		Path file = Paths.get("test.dat");

		try (DataWriter writer = new DataWriter(file)) {

			byte[] data = "A VERY STRANGE TEST GOING ON HERE!\n".getBytes();

			ExecutorService executionService = Executors.newFixedThreadPool(16);

			for (int i = 0; i < 64; i++) {

				executionService.execute(new DataAppender(writer, data));
			}

			executionService.shutdown();

			executionService.awaitTermination(Long.MAX_VALUE,
					TimeUnit.MILLISECONDS);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static class DataAppender implements Runnable {

		private static int count = 1;

		private DataWriter writer;

		private byte[] data;

		public DataAppender(DataWriter writer, byte[] data) {
			
			this.writer = writer;
			
			this.data = data;
		}

		public void run() {

			try {

				long id = writer.appendData(data);
				
				System.out.println("\nWriting: " + count++ + ";\tPosition: " + id);

			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				e.printStackTrace();
			}
		}
	}
}

