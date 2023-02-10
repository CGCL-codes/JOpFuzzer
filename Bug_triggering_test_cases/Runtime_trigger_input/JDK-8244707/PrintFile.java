
package Test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class PrintFile implements SerialPortEventListener {
	@SuppressWarnings("rawtypes")
	static Enumeration ports;

	static CommPortIdentifier pID;

	InputStream inStream;
	static OutputStream outStream;
	
	BufferedInputStream bufinStream;
	static BufferedOutputStream bufoutStream;

	SerialPort serPort;

	public PrintFile() throws Exception {
		serPort = (SerialPort) pID.open("PortReader", 2000);

		outStream = serPort.getOutputStream();
		inStream = serPort.getInputStream();

		serPort.addEventListener(this);

		serPort.notifyOnDataAvailable(true);

		serPort.setSerialPortParams(38600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
	}

	public void serialEvent(SerialPortEvent event) {
		System.out.println("ÃªÂ°ÂÃ¬Â§ÂÃ«ÂÂ¨");
		switch (event.getEventType()) {
		case SerialPortEvent.BI:
			System.out.println("SerialPortEvent.BI occurred");
		case SerialPortEvent.OE:
			System.out.println("SerialPortEvent.OE occurred");
		case SerialPortEvent.FE:
			System.out.println("SerialPortEvent.FE occurred");
		case SerialPortEvent.PE:
			System.out.println("SerialPortEvent.PE occurred");
		case SerialPortEvent.CD:
			System.out.println("SerialPortEvent.CD occurred");
		case SerialPortEvent.CTS:
			System.out.println("SerialPortEvent.CTS occurred");
		case SerialPortEvent.DSR:
			System.out.println("SerialPortEvent.DSR occurred");
		case SerialPortEvent.RI:
			System.out.println("SerialPortEvent.RI occurred");
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
			System.out.println("SerialPortEvent.OUTPUT_BUFFER_EMPTY occurred");
			break;
		case SerialPortEvent.DATA_AVAILABLE:
			System.out.println("SerialPortEvent.DATA_AVAILABLE occurred");
		
			byte[] readBuffer = new byte[16];
			bufinStream = new BufferedInputStream(inStream, readBuffer.length);
			int numBytes;
			
			try {
				while ((numBytes = bufinStream.read(readBuffer)) > -1) {
					System.out.println("ÃªÂ°ÂÃ¬Â§ÂÃªÂ°ÂÃ¬Â§Â");
				}
				System.out.print(new String(readBuffer));
			} catch (IOException ioe) {
				System.out.println("Exception " + ioe);
			}
			break;
		}
	}

	public static void main(String[] args) throws Exception {
		ports = CommPortIdentifier.getPortIdentifiers();

		while (ports.hasMoreElements()) {
			pID = (CommPortIdentifier) ports.nextElement();
			System.out.println("Port " + pID.getName());

			if (pID.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				if (pID.getName().equals("COM3")) {
					PrintFile pReader = new PrintFile();
					System.out.println("COM3 found");
				}
			}
		}
		
		int[] command = { 0x3A, 0x30, 0x31, 0x30, 0x33, 0x30, 0x30, 0x30, 0x39, 0x30, 0x30, 0x30, 0x31, 0x46, 0x32,
				0x0D, 0x0A };
		for (int i = 0; i < command.length; i++) {
			outStream.write(command[i]);
			System.out.println(command[i]);
		}

	}

}

