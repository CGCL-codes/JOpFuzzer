
import jssc.*;

public class Main {

    public static void main(String[] args) {

        // Pass port number thru argument
        String port = args[0];

        SerialPort serialPort = new SerialPort(port);
        try {
            System.out.println("Opening port " + port + " ...");
            serialPort.openPort();

            serialPort.setParams(SerialPort.BAUDRATE_9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);

            serialPort.writeString("COM3 opened !!!");

            PortReader portReader = new PortReader(serialPort);

            serialPort.addEventListener(portReader, SerialPort.MASK_RXCHAR);

            serialPort.closePort();
        } catch (Exception e) {
            System.out.println("There are an error on writing string to port Ñ: " + e);
        }

    }
}
