
package test;

import java.net.URL;
import java.io.*;

public class Test
{
	public static void main(String[] a)throws Exception
	{
		URL url = new URL("http://java.sun.com");
		System.out.println("hello, linux 64");

	char c[] = new char[1];

	    Reader inputReader = null;

		InputStream ist = url.openStream();
		inputReader = new InputStreamReader(ist);
		while(inputReader.read(c, 0, 1) == 1) {
		    System.out.print(c[0]);
		}

		inputReader.close();
	}
}

