
import java.io.IOException;

public class JDKProcessExecutionTest
{

    public static void main(String[] args)
    {
        if (args.length > 1) {
            //children
            for(;;) {
                System.out.println(" children! ");
                try {
                    Thread.sleep(30 * 1000);
                }
                catch (InterruptedException e) {
                    //just ignore...
                }
            }
        }
        else {
            //father

            try {
                Process p = Runtime.getRuntime().exec("java -cp . JDKProcessExecutionTest create child");

                for(;;) {
                    System.out.println(" father! ");
                    try {
                        Thread.sleep(30 * 1000);
                    }
                    catch (InterruptedException e) {
                        //just ignore...
                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
