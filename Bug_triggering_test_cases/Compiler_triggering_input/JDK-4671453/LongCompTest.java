public class LongCompTest {

    public static void main(String[] args) {
        System.out.println("\n--[2 Longs compare problem]--------------------");
        long l1 = 9223372034707292160L;
        long l2 = -9223372034707292160L;
        System.out.println("MAX_LONG: " + Long.MAX_VALUE );
        System.out.println("MIN_LONG: " + Long.MIN_VALUE );
        System.out.println("l1: " + l1);
        System.out.println("l2: " + l2);
        System.out.println("When comparing l1 with l2 - the result is [" + (l1 == l2
        )+"]...");
    }
}