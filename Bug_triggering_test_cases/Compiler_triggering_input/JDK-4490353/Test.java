import java.sql.*;
import oracle.sql.*;
import oracle.jdbc.driver.*;
public class Test {
  public static void main(String[] args) throws Throwable {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl","user","password");
    PreparedStatement pstmt=conn.prepareStatement("SELECT the_column from the_table");
    OracleResultSet rset=(OracleResultSet)pstmt.executeQuery();
    rset.setFetchSize(500);
    long t0=System.currentTimeMillis();
    int n=0;
    for (;rset.next();n++)
      rset.getNUMBER(1).longValue(); // somewhat faster than rset.getLong(1);
    System.out.println(""+n+" "+(System.currentTimeMillis()-t0));
    rset.close();
    pstmt.close();
  }
}