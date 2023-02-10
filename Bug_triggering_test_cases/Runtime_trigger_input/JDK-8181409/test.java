
 // Use the JDBC driver  
    import java.sql.*;  
    import com.microsoft.sqlserver.jdbc.*; 
   
 
    
        public class StoredProcedure {  

            // Connect to your database.  
            // Replace server name, username, and password with your credentials  
            public static void main(String[] args) {  
            	String name = "****";
            	String userName = "*****";
            	String password = "*****";

            	String url = "jdbc:sqlserver://****:1433;databaseName=****";

            	
            	Connection conn = DriverManager.getConnection(url, userName, password);
                    	
                	

                // Declare the JDBC objects.  
                CallableStatement cstmt = null;
                ResultSet rs = null;

                try {  
                    cstmt = connection.getConnection().prepareCall( "{call usp_GetCensusHistoryByLoc}");
                    cstmt.execute();
                    rs = cstmt.getResultSet();	
 
                    

                }  
                catch (Exception ex) {
                    Logger.getLogger(JdbcStoredProcsExample.class.getName()).log(
                            Level.SEVERE, null, ex);
                } finally {
                    if (rs != null) {
                        try {
                            rs.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(JdbcStoredProcsExample.class.getName()).log(
                                    Level.WARNING, null, ex);
                        }
                    }
                    if (cstmt != null) {
                        try {
                            cstmt.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(StoredProcedure.class.getName()).log(
                                    Level.WARNING, null, ex);
                        }

               
        }     		
   
   }
        
}
            
        }
