
import java.awt.*;

public class MyService {
    public static void main(String argv[]) {
	Frame frame = new Frame();
	frame.dispose();
	
	try {
	    Thread.sleep(100000000);
	} catch (InterruptedException e) {
	}
    }
}
