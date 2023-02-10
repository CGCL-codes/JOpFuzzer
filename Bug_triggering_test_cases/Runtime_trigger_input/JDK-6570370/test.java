
/*
 * Created on 07.06.2007
 *
 */
package de.kimmeringer.jnicrash;

/**
 * Class trying to crash the JNI as being observed in a real project
 * @author Lothar Kimmeringer &lt;###@###.###>
 *
 */
public class JNICrasher {
    
    private static native int pinArray(byte[] data);
    private static native void releaseArray(byte[] data, int address);

    /**
     * Creates a new instance of JNICrasher
     */
    public JNICrasher() {
    }
    
    /**
     * Main method to be called to start the program
     * @param args Calling arguments
     * @throws Exception Will be thrown if there was an error while
     * execution
     */
    public final static void main(String[] args) throws Exception{
        System.loadLibrary("JNICrasher");
        
        JNICrasher jc = new JNICrasher();
        jc.execute(args);
    }
    
    private void execute(String[] args) throws Exception{
        int numThreads = 1;
        if (args.length > 0){
            numThreads = Integer.parseInt(args[0]);
        }
        for (int i = 0; i < numThreads; i++){
            Executer ex = new Executer();
            ex.setName("Executing thread " + (i + 1));
            ex.start();
            System.out.println("Thread " + ex.getName() + " started");
        }
    }
    
    private class Executer extends Thread{
    
        /**
         * Main run-method
         */
        public void run() {
            try{
                int counter = 1;
                while (true){
                    byte[] bytes = new byte[512];
                    for (int i = 0; i < bytes.length; i++){
                        bytes[i] = (byte) (Math.random() * 257);
                    }
                    int address = pinArray(bytes);
                    Thread.sleep(100);
                    releaseArray(bytes, address);
                    if (counter == 1){
                        System.out.println("survived the first try");
                    }
                    counter++;
                    if (counter % 100 == 0){
                        System.out.println("Still doing the rounds");
                    }
                }
            }
            catch(Throwable t){
                System.err.println("thread " + getName() + " died");
                t.printStackTrace();
            }
        }
    }
}

