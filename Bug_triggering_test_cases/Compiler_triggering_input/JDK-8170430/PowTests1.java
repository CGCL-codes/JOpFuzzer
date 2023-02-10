public class PowTests1 { 
    private PowTests1(){} 

    /* 
     * Test cross-product of different kinds of arguments. 
     */ 
    static void testCrossProduct() { 
        final double x = 0.9999999999999999; 
        final double y = (double) ((1L<<53)); 
        final double b = Math.pow(x, y); 
        final double a = f3ns(x, y); 
        System.out.println("a: " + a + " b: " + b); 
        if (a != b) { 
            System.out.println("failed"); 
        } 
    } 


    static double f3ns(double x, double y) { 
        isFinite(y); //// 
        switch(0) { //// 
        case 0: 
            return Math.pow(x, y); 
        default: 
            return Math.pow(x, y); 
        } 
    } 

    static boolean isFinite(double a) { 
        return (0.0 * a == 0); 
    } 

    public static void main(String [] argv) { 
        testCrossProduct(); 
    } 
}
