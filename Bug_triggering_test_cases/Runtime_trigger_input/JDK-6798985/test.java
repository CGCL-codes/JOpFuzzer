
import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;

/**
 * @author Jean-Francois Im
 */
public class ShutdownHookBug {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				try {
					// Write "Hello " to a file
					File myFile = new File(System.getProperty("user.home") + File.separator + "shutdownhookbug.log");
					FileOutputStream outputStream = new FileOutputStream(myFile);
					outputStream.write("Hello ".getBytes());
					outputStream.flush();

					// Sleep for 5s
					sleep(5000);

					// Write "world!" and close the file
					outputStream.write("world!".getBytes());
					outputStream.flush();
					outputStream.close();
				} catch(Exception e) {
					// Shouldn't happen.
				}
			}
		});
	}
}
