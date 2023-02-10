import java.awt.Graphics2D; 
import java.awt.image.BufferedImage; 
import java.util.Arrays;

public class Test { 
    public static void main(String[] args) { 
        BufferedImage i = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB); 
        Graphics2D g = i.createGraphics(); 

        int x = 0; 

        while (true) { 
            int[] a = new int[2]; 
            int b = i.getWidth(); 

            Arrays.fill(a, 1); 
            Arrays.fill(a, 1+b); 

            g.drawPolygon(a, a, 2); 

            //if (args.length > 0) System.out.println(++x + ", "); 
        } 
    } 
} 
