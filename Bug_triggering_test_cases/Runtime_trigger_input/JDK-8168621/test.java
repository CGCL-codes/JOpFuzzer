
package com.hp.middleware.solr.sql;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;




public class TestConnection {

	public static void btnConsultaActionPerformed() {
	
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			// Establecemos la conexión

					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerXADataSource").newInstance();


			
			
			


			
			String connectionUrl = "jdbc:sqlserver://127.0.0.1:34001;dataBaseName=HIST_DFW_PRIMA_DATA;"+
		     "databaseName=AdventureWorks;integratedSecurity=true;" +  
		     "encrypt=true;trustServerCertificate=true";
			
			
			System.out.println("yeah");

		
			String user = "uDFWdata";
		
			String pass = "**************";

			connection = DriverManager.getConnection(connectionUrl, user, pass);

		
			System.out.println("connected");
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			if (connection != null)
				try {
					connection.close();
				} catch (Exception e) {
				}

			System.out.println("bloque de código ejecutado siempre");

		}

	}

	public static void main(String[] args) {

		btnConsultaActionPerformed();
		
		//DB.dameMail();
		

	}

	
}

		

	
