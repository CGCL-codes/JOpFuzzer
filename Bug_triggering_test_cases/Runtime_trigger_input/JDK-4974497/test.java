
/**
 * Created by IntelliJ IDEA.
 * User: dclements
 * Date: Oct 7, 2003
 * Time: 3:04:35 PM
 * To change this template use Options | File Templates.
 */
public class ThreadTest implements Runnable{
    private int i;

    public void run() {
        System.out.println("Started " +i );
        while(true)
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            }
    }

    ThreadTest(int i) {

        this.i = i;
    }
    public static void main( String [] args) {
        int arg = 0;
        boolean daemon = args[arg++].equals("true");
        int sleep = Integer.parseInt(args[arg++]);
        int i = 0;
        try {
        while ( true) {
            Thread t = new Thread( new ThreadTest(i));
            t.setName("Test " + i++);

            t.setDaemon(daemon);
            t.start();
            try {
                Thread.currentThread().sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            }
        }

    }  catch( Exception e){
                   e.printStackTrace();
            while ( true)  {
                try {
                    Thread.currentThread().sleep(10000);
                } catch (InterruptedException e1) {
                    System.out.println("Sleeping");
                    e1.printStackTrace();  //To change body of catch statement use Options | File Templates.
                }
            }

            }
    }

}

