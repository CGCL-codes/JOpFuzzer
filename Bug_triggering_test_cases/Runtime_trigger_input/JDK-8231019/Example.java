
// Import the SQL Server JDBC Driver classes
import java.sql.*;

class Example
{
       public static void main(String args[])
       {
       try
       {
            // Load the SQLServerDriver class, build the
            // connection string, and get a connection
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
          String connectionUrl = "jdbc:sqlserver://localhost;databaseName=NBWA;integratedSecurity=true";

            Connection con = DriverManager.getConnection(connectionUrl);
            System.out.println("Connected.");

            // Create and execute an SQL statement that returns some data.

            //commented out
           String SQL = "SELECT REPORTING_MANUFACTURER_NAME FROM ManWeeklyTotals";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            // Iterate through the data in the result set and display it.
            while (rs.next())
            {
               System.out.println(rs.getString(1) + " " + rs.getString(2));
            }

       }
       catch(Exception e)
       {
            System.out.println(e.getMessage());
            System.exit(0);
       }
    }
}
