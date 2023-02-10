
import java.io.FileInputStream;
import java.io.IOException;

public class Foo {
	public static void main(String[] args) throws IOException  {
		FileInputStream fis = new FileInputStream("/etc/hosts");
		System.out.println(fis.available()); // Works fine

		fis = new FileInputStream("/proc/cpuinfo");
		System.out.println(fis.available()); // Throws IOException

	}
}

