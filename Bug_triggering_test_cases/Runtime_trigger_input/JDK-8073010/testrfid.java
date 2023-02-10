
import java.sql.*;
import java.text.*;

public class testrfid {
    //==============================================================================
    public static void main(String args[]) {
        while (true) {
            StatusClass status = getPersonByTagID("0000 0000 0000 0000 0000 5260");
        }
    }

    //==============================================================================
    public static StatusClass getPersonByTagID(String tagid) {
        StatusClass status = new StatusClass();
        status.setSuccess(true);
        Connection conn = null;
        CallableStatement ps = null;
        ResultSet rs = null;
        int numberRows = 0;

        System.out.println("Opening connection");
        try {
            conn = makeConnection();
            System.out.println("Creating Callable statement");
            ps = conn.prepareCall("{CALL uspTestRFID(?, ?)}");
            System.out.println("Setting param 1");
            ps.setString(1, tagid);
            System.out.println("Setting param 2");
            ps.registerOutParameter(2, java.sql.Types.INTEGER);
        } catch (SQLException sq1) {
            System.out.println("getStaffByTagID.setup3:" + sq1.getErrorCode() + ":" + sq1.getSQLState() + ":" + sq1.getMessage());
        } catch (Exception g) {
            System.out.println("getStaffByTagID:setup4: " + g.getMessage());
        }
        System.out.println("Executing query");
        try {
            rs = ps.executeQuery();
            status.setValue(ps.getInt(2));  // get the type of person -- 1 = student  2 = staff
            System.out.println("getPersonByTagID2:" + status.getValue());
        } catch (SQLException sq) {
            System.out.println("getStaffByTagID.read1:" + sq.getErrorCode() + ":" + sq.getSQLState() + ":" + sq.getMessage());
        } catch (Exception f) {
            System.out.println("getStaffByTagID:read2: " + f.getMessage());
        }
        System.out.println("Walking thru resultset");
        try {
            status.setObject(new StaffClass());
            while (rs.next()) {
                numberRows++;
            }
        } catch (Exception ee) {
        }
        System.out.println("Closing SQL connections");
        try {
            if (ps != null)
                ps.close();
            if (conn != null)
                conn.close();
            conn = null;
            ps = null;
        } catch (Exception e) {
            System.out.println("getStaffByTagID:close: " + e.getMessage());
        }
        status.setStatusCode(numberRows);
        System.out.println("Exit getStaffByTagID");
        return status;
    }

//==============================================================================

    /**
     * This method creates the SQL connection for a SQL session.
     *
     * @return The connection object.
     */
    public static Connection makeConnection() {
        String database = "jdbc:odbc:Academy";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(database);
        } catch (Exception e) {
            java.util.Date currTime = new java.util.Date();
            SimpleDateFormat ldf = new SimpleDateFormat("yyyy.MM.dd kk:mm:ss.SSS");
            String lds = ldf.format(currTime);
            System.out.println(lds + ":makeConnection:" + e.getMessage());
            System.out.println("Connection string = " + database);
            System.out.println("ACEserver shutting down");
            System.exit(1);
        }
        return conn;
    }

}
