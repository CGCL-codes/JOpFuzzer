import oracle.sql.NUMBER;

public class OracleTest {
    public static void main(String[] args) {

        byte[] b = new byte[1];
        b[0] = 0x41;
        NUMBER.isValid(b);

        long then = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            NUMBER.isValid(b);
        }
        long now = System.currentTimeMillis();
        System.out.println("TOOK " + (now - then) + " milliseconds");

        then = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            NUMBER.isValid(b);
        }
        now = System.currentTimeMillis();
        System.out.println("TOOK " + (now - then) + " milliseconds");
    }
}