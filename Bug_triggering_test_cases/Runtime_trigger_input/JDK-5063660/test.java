public class test{
   public static void main(String[] args) throws IOException {
        final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s;
        do {
            s = br.readLine();
            System.out.println("s = " + s);
        } while (!"stop".equals(s));
    }

}