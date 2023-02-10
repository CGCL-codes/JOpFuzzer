
public class LoopbackTest
{
	
	private SerialPort serialPort;
	private OutputStream outStream;
	private InputStream inStream;
	
	public void connect( String portName) throws IOException
	{
		try {
			// Obtain a CommPortIdentifier object for the port you want to open
			CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier( portName );
			
			// Get the port's ownership
			serialPort = (SerialPort) portId.open( "Demo application", 5000 );
			
			// Set the parameters of the connection.
			setSerialPortParameters();
			
			// Open the input and output streams for the connection.
			// If they won't open, close the port before throwing an exception.
			outStream = serialPort.getOutputStream();
			inStream = serialPort.getInputStream();
		} catch ( NoSuchPortException e ) {
			throw new IOException( e.getMessage() );
		} catch ( PortInUseException e ) {
			throw new IOException( e.getMessage() );
		} catch ( IOException e ) {
			serialPort.close();
			throw e;
		}
	}
	
	/**
	 * Get the serial port input stream
	 * @return The serial port input stream
	 */
	public InputStream getSerialInputStream()
	{
		return inStream;
	}
	
	/**
	 * Get the serial port output stream
	 * @return The serial port output stream
	 */
	public OutputStream getSerialOutputStream()
	{
		return outStream;
	}
	
	/**
	 * Register event handler for data available event
	 * 
	 * @param eventHandler Event handler
	 */
	public void addDataAvailableEventHandler( SerialPortEventListener eventHandler)
	{
		try {
			// Add the serial port event listener
			serialPort.addEventListener( eventHandler );
			serialPort.notifyOnDataAvailable( true );
		} catch ( TooManyListenersException ex ) {
			System.err.println( ex.getMessage() );
		}
	}
	
	/**
	 * Disconnect the serial port
	 */
	public void disconnect()
	{
		if ( serialPort != null ) {
			try {
				// close the i/o streams.
				outStream.close();
				inStream.close();
			} catch ( IOException ex ) {
				// don't care
			}
			// Close the port.
			serialPort.close();
		}
	}
	
	/**
	 * Sets the serial port parameters to 57600bps-8N1
	 */
	private void setSerialPortParameters() throws IOException
	{
		int baudRate = 57600; // 57600bps
		
		try {
			// Set serial port to 57600bps-8N1..my favourite
			serialPort.setSerialPortParams( baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
			        SerialPort.PARITY_NONE );
			
			serialPort.setFlowControlMode( SerialPort.FLOWCONTROL_NONE );
		} catch ( UnsupportedCommOperationException ex ) {
			throw new IOException( "Unsupported serial port parameter" );
		}
	}
	
	@SuppressWarnings( "empty-statement" )
	public static void main( String[] args)
	{
		// Start the loopback testing
		String testString = "The quick brown fox jumps over the lazy dog";
		// Timeout: 1s
		final int TIMEOUT_VALUE = 1000;
		
		// Get the serial port
		LoopbackTest loopbackTest = new LoopbackTest();
		try {
			loopbackTest.connect( "/dev/ttyS0" );
			
			InputStream inStream = loopbackTest.getSerialInputStream();
			OutputStream outStream = loopbackTest.getSerialOutputStream();
			for ( int i = 0; i < testString.length(); i++ ) {
				// Write to serial port
				outStream.write( testString.charAt( i ) );
				
				// wait until we got the loopback data
				// Dont forget the timeout
				long startTime = System.currentTimeMillis();
				long elapsedTime;
				do {
					elapsedTime = System.currentTimeMillis() - startTime;
				} while ( ( elapsedTime < TIMEOUT_VALUE ) && ( inStream.available() == 0 ) );
				
				// if ( elapsedTime < TIMEOUT_VALUE ) {
				// Compare the result
				int readChar = inStream.read();
				if ( readChar != testString.charAt( i ) ) {
					System.err.println( "Received: " + readChar + " Sent:" + testString.charAt( i ) );
				}
				// } else {
				// // Get out from the loop..
				// System.err.println( "Received nothing from serial port" );
				// break;
				// }
			}
		} catch ( IOException ex ) {
			System.err.println( ex.getMessage() );
		}
		
		System.out.println( "Test done\r\n" );
		loopbackTest.disconnect();
	}
}
