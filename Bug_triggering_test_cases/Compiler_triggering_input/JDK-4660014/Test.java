import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

public class Test {
    public static void main(String args[]) {
        File fileSource = new File(args[0]);

        System.out.println("fileSource = " + fileSource);
        try {
            BufferedImage bufferedImage = ImageIO.read(fileSource);
            System.out.println("bufferedImage = " + bufferedImage);
        } catch(Exception e) {
            System.out.println("Unexpected exception.");
            e.printStackTrace(System.out);
        }
        System.out.println("End of the test.");
        System.exit(1);
    }
}