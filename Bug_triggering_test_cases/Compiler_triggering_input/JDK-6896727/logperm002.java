public class logperm002 {

    final static String testString = "abracadabra";
    public static void main(String args[]) {
        String params[][] = {
                {"control", testString}
        };
        for (int i=0; i<params.length; i++) {
            try {
                System.out.println("Params :" + testString + " and " + params[i][0] + ", " + params[i][1]);
            } catch (Exception e) {}
        }
    }
}