
package org.hid4java;

import org.hid4java.event.HidServicesEvent;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * <p>Demonstrate the USB HID interface using a production Bitcoin Trezor</p>
 *
 * @since 0.0.1
 * Â 
 */
public class UsbHidTrezorV1Example implements HidServicesListener {

  
  static final int PACKET_LENGTH = 5;
  static final int VENDOR = 0x483;
  static final int PRODUCT = 0x5750;
 /* static final int VENDOR = 0x1bcf;
  static final int PRODUCT = 0x7;*/
  private HidServices hidServices;

  public static void main(String[] args) throws HidException {

    UsbHidTrezorV1Example example = new UsbHidTrezorV1Example();
    example.executeExample();

  }

  public void executeExample() throws HidException {

    System.out.println("Loading hidapi...");

    // Get HID services
    hidServices = HidManager.getHidServices();
    hidServices.addHidServicesListener(this);

    System.out.println("Enumerating attached devices...");
    String path=null;
    // Provide a list of attached devices
    for (HidDeviceInfo hidDeviceInfo : hidServices.getAttachedHidDevices()) {
      System.out.println(hidDeviceInfo);
      if(hidDeviceInfo.getProductId()==PRODUCT && hidDeviceInfo.getVendorId()==VENDOR)
    	  path=hidDeviceInfo.getSerialNumber().toString();
    }
    
    // Open the ChronoVision device by Vendor ID and Product ID with wildcard serial number
    HidDevice chrono = hidServices.getHidDevice(VENDOR, PRODUCT, null);
    System.out.println(chrono);
    
    if (chrono != null) {
      System.out.println("Connected");
      // Device is already attached so send message
      sendInitialise(chrono);
    } else {
      System.out.println("Waiting for ChronoVision to attach...");
    }
    // Stop the main thread to demonstrate attach and detach events
    sleepUninterruptibly(1, TimeUnit.HOURS);

  }

  @Override
  public void hidDeviceAttached(HidServicesEvent event) {

    System.out.println("Device attached: " + event);
    System.out.println(event.getHidDeviceInfo().getVendorId() == VENDOR && event.getHidDeviceInfo().getProductId() == PRODUCT);
    if (event.getHidDeviceInfo().getVendorId() == VENDOR &&
      event.getHidDeviceInfo().getProductId() == PRODUCT) {

      // Open the Chronovision device by Vendor ID and Product ID with wildcard serial number
      HidDevice chrono = hidServices.getHidDevice(VENDOR, PRODUCT, null);
      System.out.println(chrono);
      if (chrono != null) {
        sendInitialise(chrono);
      }

    }

  }

  @Override
  public void hidDeviceDetached(HidServicesEvent event) {

    System.err.println("Device detached: " + event);

  }

  @Override
  public void hidFailure(HidServicesEvent event) {

    System.err.println("HID failure: " + event);

  }

  private void sendInitialise(HidDevice chrono) {
	  System.out.println("Iniitalizing");
	int count =0;
	
	
	// => The Report length is 4 + Report ID
    byte buff[] = {0x0,0x0,0x0,0x0,0x0,0x0};
    
    //byte1 line read request(0x00) over broadcast(0x0F)
    // => LINE is not supported yet, see communication protocol
    // => Byte 1 = MODULE/MODADDR is 0x0F for broadcast (why do you use a "<<" operator?)
    // => YOUR CODE: buff[1] = 0x00 | (0x0f<<1);
    // => MODIFIED CODE:
    buff[0] = 0x0F;
   
    //byte 2 module
    //USB
    // => Byte 2: REGISTER is e. g. 9 or 0x09 (ROTPERMIN), REGRW = 1 for writing
    // => YOUR CODE: buff[2] = 0x01;
    // => YOUR CODE: // move bit to appro
    // => YOUR CODE: buff[2]<<=6;
    // => YOUR CODE: //read from line master
    // => YOUR CODE: buff[2] |= 0x00;
    // => MODIFIED CODE:
    buff[1] = (byte) (0x00 | (0x01 << 6));
   
    
    buff[2] = (byte)(0x00<<7|0x09);
    //byte 3 register access read only
    // => Byte 3 is VALUE0 / Byte 4 is VALUE1
    // => YOUR CODE: buff[3] = 0x0<<7;
    // => YOUR CODE: //read first address
    // => YOUR CODE: buff[3] |= 0x0;
    // => MODIFIED CODE:
    
    buff[3] = (byte)(0);
    
    
    count = chrono.sendFeatureReport(buff, (byte)1);
    System.out.println("Bytes Written = " +count);
    
    /*count = chrono.getFeatureReport(buff, (byte)1);
    System.out.println("Byte read : "+count);
    if(count >0){
    	System.out.println(new String(buff, 0, count));
    }
    String error = chrono.getLastErrorMessage();
    System.out.println(error);*/
    buff= new byte[13];
   	count = chrono.read(buff);
   	chrono.setNonBlocking(true);
   	System.out.println(count);
    if(count >0){
    	for(byte b : buff)
    		System.out.println(b);
    }
    
  }

  /**
   * Invokes {@code unit.}{@link java.util.concurrent.TimeUnit#sleep(long) sleep(sleepFor)}
   * uninterruptibly.
   */
  public static void sleepUninterruptibly(long sleepFor, TimeUnit unit) {
    boolean interrupted = false;
    try {
      long remainingNanos = unit.toNanos(sleepFor);
      long end = System.nanoTime() + remainingNanos;
      while (true) {
        try {
          // TimeUnit.sleep() treats negative timeouts just like zero.
          NANOSECONDS.sleep(remainingNanos);
          return;
        } catch (InterruptedException e) {
          interrupted = true;
          remainingNanos = end - System.nanoTime();
        }
      }
    } finally {
      if (interrupted) {
        Thread.currentThread().interrupt();
      }
    }
  }

}

