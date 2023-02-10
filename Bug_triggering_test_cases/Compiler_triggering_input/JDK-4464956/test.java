import java.sql.*;
class test {
    public static void main(String args[]) {

java.util.Random r = new java.util.Random();
// loop is important!
for (int i=1; i<20000; i++) {
Timestamp st1 = new Timestamp(r.nextInt());
}
Timestamp ts1 = new Timestamp(1234560000); //step Create Timestamp
        System.out.println("ts1 = " + ts1);
        Timestamp ts2 = new Timestamp(1234567000); //step Create Timestamp
        System.out.println("ts2 = " + ts2);
        Timestamp ts3 = new Timestamp(1234569000); //step Create Timestamp
        System.out.println("ts3 = " + ts3);
        if (!(ts1.before(ts2) && ts2.before(ts3) && ts1.before(ts3) ))
System.err.println("Failed");

    }
}