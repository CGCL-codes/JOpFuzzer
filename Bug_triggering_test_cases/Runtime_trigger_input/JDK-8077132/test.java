
import java.net.*;
import java.io.*;

public class Scan2 {
	public static void main(String[] args) throws Exception {
		URL url = new URL("http://accelerated-e.com");
		URLConnection test = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(test.getInputStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null)
			System.out.println(inputLine);
		in.close();
	}
}
