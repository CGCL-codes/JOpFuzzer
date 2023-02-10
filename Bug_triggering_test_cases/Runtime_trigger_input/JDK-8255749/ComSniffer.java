
package com.cryptera.util.comsniffer;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import org.apache.log4j.Category;
import org.apache.log4j.PropertyConfigurator;

import com.cryptera.util.ByteArray;
import com.cryptera.util.LimitedQueue;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class ComSniffer {

  private final static Category cat = Category.getInstance(ComSniffer.class);

  protected static final int RETRANSMITS_ALLOWED = 3;
  protected static final int BYTE_TIMEOUT = 250;
  private boolean responseReceived;
  protected DataInputStream inputFromSerialPortStream;
  protected OutputStream outputFromSerialPortStream;

//  protected DataInputStream inputPortA;
//  protected OutputStream outputPortA;
//  protected DataInputStream inputPortB;
//  protected OutputStream outputPortB;
  protected SerialPort portA;
  protected SerialPort portB;
  protected ReceiveThread receiveThreadA;
  protected ReceiveThread receiveThreadB;
  protected TransmitThread transmitThreadA;
  protected TransmitThread transmitThreadB;
  private LimitedQueue queueAB = new LimitedQueue(10);
  private LimitedQueue queueBA = new LimitedQueue(10);
  private Integer frame;
  private ErrorGenerator errgen;
  
  static boolean showpoll = false;
  static byte triggerbyte = 0x00;
  static int errornumber = 0;

  // events
  final static int TIMEOUT = -1;


  /**
   * transmit lock
   */
  protected Object txLock = new Object();

  /**
   * used to determine wether a resend is reqiered
   */
  private boolean resendRequired = false;
  
  
  public static boolean directlink = true;

  public ComSniffer(String hostport, int baudA, String deviceport, int baudB) {
	  this(hostport, baudA, deviceport, baudB, false);
  }


  public ComSniffer(String hostport, int baudA, String deviceport, int baudB, boolean showpoll) {
	  ComSniffer.showpoll = showpoll;
	  
	  cat.info("Poll logging is "+(showpoll ? "on!" : "off!"));
	  
	  // Set the library path from the ini-file before trying to load the COM-port drivers
	  // If this isn't set the application will attempt to load drivers from the bin-folder of the Java installation
	  System.setProperty("rxtx.libpath", "./drivers");
	  
	  
	  
    errgen = new ErrorGenerator();
    // Define in/out streams
    portA = setupComPort(hostport, baudA, "PortA", SerialPort.PARITY_NONE);
    portB = setupComPort(deviceport, baudB, "PortB", SerialPort.PARITY_EVEN);
    try {
      // Setup two COM ports
//      outputPortA = portA.getOutputStream();
//      outputPortB = portB.getOutputStream();
      
     // PortA is considered the host, PortB is the device

      //Setup A->B receive-thread and transmit-thread
      receiveThreadA = new ReceiveThread();
      receiveThreadA.setName("FromHost");
      receiveThreadA.setPort(portA, queueAB);
      receiveThreadA.start();

      transmitThreadA = new TransmitThread();
      transmitThreadA.setName("ToDevice");
      transmitThreadA.setMark("-->");
      transmitThreadA.setPort(queueAB, portB);
      if(directlink) // This means that the bytes are passed on without going through the parser
    	  transmitThreadA.setCommandParser(new SankyoCommandParser("--> to card reader: ", 0, null, showpoll));
      else
          transmitThreadA.setCommandParser(new SankyoCommandParser("--> to card reader: ", 0, transmitThreadA, showpoll));
      transmitThreadA.start();

      //Setup B->A receive-thread and transmit-thread
      receiveThreadB = new ReceiveThread();
      receiveThreadB.setName("FromDevice");
      receiveThreadB.setPort(portB, queueBA);
      receiveThreadB.start();

      transmitThreadB = new TransmitThread();
      transmitThreadB.setName("ToHost");
      transmitThreadB.setMark("<--");
      transmitThreadB.setPort(queueBA, portA);
      if(directlink) // This means that the bytes are passed on without going through the parser
    	  transmitThreadB.setCommandParser(new SankyoCommandParser("<-- to EPP: ", 0, null, showpoll));
      else
    	  transmitThreadB.setCommandParser(new SankyoCommandParser("<-- to EPP: ", 0, transmitThreadB, showpoll));
      transmitThreadB.start();

      cat.info("All sniffer receive and transmit threads started. PortA ("+hostport+") <--> PortB ("+deviceport+")");
    }
    catch(Exception piuex){
      System.out.println("Exception in COM port constructor!"+piuex);
    }

  }

  public void sendTestMessage(String text) {
    try {
      cat.debug("Writing text to port A: "+text);
      transmitThreadA.addToQueue(text.getBytes());
//      cat.debug("Writing text to port B: "+text);
//      transmitThreadB.addToQueue(text.getBytes());
    }
    catch(Exception piuex){
      System.out.println("Exception in sendTextMessage!"+piuex);
    }


  }

  public void setHostEnd(String hostend) {

  }


  /**
   * Receiver takes care of the reception of bytes. When a byte[] has been received
   * it is marshalled to the statemachine, that determines what to do with it...
   * The thread is sleeping until an event from the communication channel wakes
   * it up.
   */
  private class ReceiveThread extends Thread implements SerialPortEventListener {
     public Object waitingForData = new Object();
     private boolean stopped = false; // Used to terminate the run method
     private byte[] input;
     private SerialPort port;
     private DataInputStream inputStream;
     private LimitedQueue queue;

     public void run() {

        try {
           /**
            * Listen for data available on serial port.
            */
           port.addEventListener(this);
           port.notifyOnDataAvailable(true);
//           port.setDTR(true);

           while (!stopped) {
              //wait until data awailable
              synchronized (waitingForData) {
                 // wait for characters to be available - or someone sets
                 // the stopped flag
                 while (inputStream.available() == 0 && !stopped) {
                    cat.debug("Just before data wait ANC");
                    waitingForData.wait();
                    cat.debug("Just after data wait ANC");
                 }
                 cat.debug("Out of inner while loop...");
              }

              if (stopped) {
                 cat.debug("Something stopped the thread - quit the loop...");
                 break;
              }

              input = new byte[inputStream.available()];
              cat.debug("--> a");
              cat.debug(inputStream.available());
              cat.debug("--> a2");
              byte[] buf = new byte[1];
              cat.debug(inputStream.read(buf));
              cat.debug(inputStream.read(buf));
              cat.debug("--> a3");
              
              inputStream.readFully(input);
              cat.debug("--> b");
              cat.debug("Putting bytes on the queue");
              queue.addBlocking(input);
              cat.debug("Reading text: "+ByteArray.toString(input));
           }
           cat.debug("Has left the while loop!");
        }
        catch (IOException ioe) { // Thrown by inputFromSerialPortStream
           cat.warn("IOException occured ", ioe);
        }
        catch (Throwable e) {
           cat.warn("Receive thread interrupted");
        }
        cat.info("Receive thread terminated");
     }

     public void serialEvent(SerialPortEvent event) {
         cat.debug("Serial event received");
        if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
           synchronized (waitingForData) {
              waitingForData.notifyAll();
           }
        }
     }

     /**
      * called to stop the thread.
      */
     public void doStop() {
        cat.debug("doStop()");
        stopped = true;
        interrupt();         //Interrupt the read, otherwise it won't stop until it receives a char
     }


     public boolean isStopped() {
        return!isAlive();
     }

     public void setPort(SerialPort p, LimitedQueue outqueue) {
       port = p;
       queue = outqueue;
       try {
         inputStream = new DataInputStream(p.getInputStream());
       } catch(Exception e) {cat.warn("Exception getting input stream for port "+p.getName()+"  "+e);}
     }     
     
  } // End of ReceiveThread


public void stopAllThreads() {
  receiveThreadA.doStop();
  receiveThreadB.doStop();
  transmitThreadA.stopThread();
  transmitThreadB.stopThread();
  portA.close();
  portB.close();
  cat.debug("stopAllThreads()");
}

private SerialPort setupComPort(String portName, int baudRate, String appName, int parity) {
  SerialPort port = null;
try{
	   cat.debug("Setting up port on: "+portName);
	   System.out.println("Setting up port on: "+portName);
   CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(portName);
   port = (SerialPort) portId.open(appName, 0);
   
   port.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN |
           SerialPort.FLOWCONTROL_RTSCTS_OUT);
   
   port.setDTR(true);
   port.setSerialPortParams(baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, parity);
}
  catch(Exception piuex){
   System.out.println("Exception in COM port creation!  For app: "+appName+"  "+piuex);
  }
  if(port!=null) cat.info("Sniffer connected to "+portName+" as port "+appName+" baud-rate "+baudRate);
  return port;
}


private class ErrorGenerator {
  public Object waitingOpposite = new Object();
  private int seqnum = 0;
  private boolean[] errorA = new boolean[10];
  private boolean[] errorB = new boolean[10];
  private byte trigger = 0x00;
  private String errtitle = "";
  private int errnum = 0; // Means no error

  public void ErrorGenerator() {
    clearErrorFlags();
  }

  public void clearErrorFlags() {
    for(int ic=0; ic<10; ic++) {
      errorA[ic] = false;
      errorB[ic] = false;
      seqnum = 0;
      errtitle = "";
    }
  }

  public String getErrortitle() {
    if(seqnum!=0)
      return errtitle;
    else
     return "";
  }
  
  public synchronized byte[] imposeError(String thread, byte[] bytes) {
    // Make a copy of the received array
    byte[] result = new byte[bytes.length];
    System.arraycopy(bytes,0,result,0,bytes.length);
    cat.debug("Checking error on bytes: "+ByteArray.toString(result)+" Sequence number is "+seqnum);
    if(seqnum==0) { // No error in progress, check if one should be started
      clearErrorFlags(); // Remove previous settings
//      cat.debug("Error settings cleared");
      trigger = triggerbyte;
      cat.debug("SeqNum="+seqnum+"  trigger="+trigger+"   Command="+bytes[3]+"  Thread: "+thread);
      // If trigger is all commands (0) or the current command and towards device start an error
//      if(((trigger==(byte)0x0)||(bytes[3]==trigger))&&thread.equalsIgnoreCase("ToDevice")) {
      if((bytes[3]==trigger)&&thread.equalsIgnoreCase("ToDevice")) {
        errnum = errornumber;
        makeError(errnum);
        cat.info("Error "+errnum+" started");
      }
    } else {
      seqnum--; // Count down
    }
    if (thread.equalsIgnoreCase("ToDevice")) {
      if(errorA[seqnum]) {
        result[bytes.length - 2] ^= 0x10; // Toggle a bit to create an error in CRC byte
        cat.info("Creates error "+errnum+" towards device");
      }
    }
    if (thread.equalsIgnoreCase("ToHost")) {
      if(errorB[seqnum]) {
        result[bytes.length - 2] ^= 0x10; // Toggle a bit to create an error in CRC byte
        cat.info("Creates error "+errnum+" towards host");
      }
    }
    cat.debug("Returning: "+ByteArray.toString(result)+" Sequence number is "+seqnum);
    return result;
  }

  private void makeError(int err) {
    cat.debug("Returned error number: "+err);
    switch(err) {
      case 1:
        seqnum = 1;
        errorA[1] = true;
        errtitle = "Error Case 1";
        break;
      case 2:
        seqnum = 2;
        errorB[1] = true;
        errtitle = "Error Case 2";
        break;
      case 3:
        seqnum = 2;
        errorA[2] = true;
        errorB[1] = true;
        errtitle = "Error Case 3";
        break;
      case 4:
        seqnum = 3;
        errorB[2] = true;
        errorA[1] = true;
        errtitle = "Error Case 4";
        break;
      case 5:
        seqnum = 4;
        errorB[3] = true;
        errorA[2] = true;
        errorB[1] = true;
        errtitle = "Error Case 5";
        break;
      case 6:
        seqnum = 5;
        errorB[4] = true;
        errorA[3] = true;
        errorB[2] = true;
        errorA[1] = true;
        errtitle = "Error Case 6";
        break;
    }
  }

}

  public static void main(String[] args) {
	  
    PropertyConfigurator.configure("./resources/log4j.properties");
  	ClassLoader cl = Thread.currentThread().getContextClassLoader();
	URL loc = cl.getResource("log4j.properties");
	System.out.println("Loading classpath for log4j.properties --> " + loc);

//	System.setProperty("rxtx.libpath", "./drivers");
//
//	JavaUtil javautil = new JavaUtil();
//	javautil.installDriver();
//	System.out.println("Lib Path:"+javautil.libPath());
	
	ComSniffer comsniffer = null;

	if(args.length>2)		
		if(args.length==3) comsniffer = new ComSniffer(args[0], 38400, args[1], 38400, args[2].equalsIgnoreCase("1"));
		else comsniffer = new ComSniffer(args[0], 38400, args[1], 38400);
	else
		comsniffer = new ComSniffer("COM2", 38400, "COM4", 38400);
//    comsniffer.sendTestMessage("Test message!");
    try {
      while(true);
//      Thread.sleep(3000);
    } catch (Exception e) {cat.debug("Exception in main sleep  "+e); }
    cat.debug("At the end!");

    comsniffer.stopAllThreads();
  }
}

