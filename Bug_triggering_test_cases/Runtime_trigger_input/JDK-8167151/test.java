
import java.sql.*;

public class Main {
	public static void main(String[] args) throws Exception {

		int taskInserCount = 0;
		int taskSelectCount = 0;
		boolean isShareConnection = false;

		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");

		Statement stat = conn.createStatement();
		stat.executeUpdate("drop table if exists people");
		stat.executeUpdate("create table people (name, occupation, age, salary)");
		if (!isShareConnection) {
			conn.close();
			conn = null;
		}

		SqlTaskInsert tasksInsert[] = { new SqlTaskInsert(conn, "Gandhi", "politics", 20, 3000) };

		SqlTaskSelecct tasksSelect[] = { new SqlTaskSelecct(conn) };

		taskInserCount = 20;
		taskSelectCount = 0;

		System.out.println("Insert query count:" + taskInserCount);
		System.out.println("Select query count:" + taskSelectCount);
		System.out.println("Sequential DB access:");

		Thread threadsInsert[] = new Thread[taskInserCount];
		for (int i = 0; i < taskInserCount; i++)

			threadsInsert[i] = new Thread(tasksInsert[0]);

		for (int i = 0; i < taskInserCount; i++) {
			threadsInsert[i].start();
			threadsInsert[i].join();
		}

		Thread threadsSelect[] = new Thread[taskSelectCount];
		for (int i = 0; i < taskSelectCount; i++)
			threadsSelect[i] = new Thread(tasksSelect[0]);

		for (int i = 0; i < taskSelectCount; i++) {
			threadsSelect[i].start();
			threadsSelect[i].join();
		}

		System.out.println();
		System.out.println("Concurrent DB access:");

		for (int i = 0; i < taskInserCount; i++)
			threadsInsert[i] = new Thread(tasksInsert[0]);

		for (int i = 0; i < taskSelectCount; i++)
			threadsSelect[i] = new Thread(tasksSelect[0]);

		for (int i = 0; i < taskInserCount; i++)
			threadsInsert[i].start();

		for (int i = 0; i < taskSelectCount; i++)
			threadsSelect[i].start();

		for (int i = 0; i < taskInserCount; i++)
			threadsInsert[i].join();
		for (int i = 0; i < taskSelectCount; i++)
			threadsSelect[i].join();

		if (isShareConnection) {
			conn.close();
		}
	}

	private static class SqlTaskSelecct implements Runnable {

		private Connection conn;
		private boolean isShareConnection;

		public SqlTaskSelecct(Connection conn) {
			this.conn = conn;
			if (this.conn == null) {
				isShareConnection = false;

			} else {
				isShareConnection = true;
			}
		}

		public void run() {

			// Connection conn = null;
			Statement stm = null;
			long startTime = System.currentTimeMillis();

			try {
				try {
					if (!isShareConnection) {
						conn = DriverManager.getConnection("jdbc:sqlite:test.db");
						System.out.println("   SQL coonection is seperated.-select");
					} else {
						System.out.println("   SQL coonection is shared.-select");
					}
					
					stm = conn.createStatement();
 
					long duration = System.currentTimeMillis() - startTime;
					ResultSet resultSet = stm.executeQuery("select * from people");
					if (resultSet.next()) {
						String name = resultSet.getString("name");
						String address = resultSet.getString("occupation");
						int age = resultSet.getInt("age");
						int salary = resultSet.getInt("salary");
					}

					System.out.println("   SQL Select completed: " + duration);
				} finally {
					if (stm != null)
						stm.close();
					if (isShareConnection == false && conn != null)
						conn.close();
				}
			} catch (SQLException e) {
				long duration = System.currentTimeMillis() - startTime;
				System.out.print("   SQL Insert failed: " + duration);
				System.out.println(" SQLException: " + e);
			}

		}
	}

	private static class SqlTaskInsert implements Runnable {
		String name, occupation;
		private int salary;
		private int age;
		private Connection conn;
		private boolean isShareConnection;

		public SqlTaskInsert(Connection conn, String name, String occupation, int age, int salary) {
			this.conn = conn;
			this.name = name;
			this.occupation = occupation;
			this.age = age;
			this.salary = salary;

			if (this.conn == null) {
				isShareConnection = false;
			} else {
				isShareConnection = true;
			}
		}

		public void run() {
			// Connection conn = null;
			PreparedStatement prep = null;
			long startTime = System.currentTimeMillis();

			try {
				try {
					if (!isShareConnection) {
						conn = DriverManager.getConnection("jdbc:sqlite:test.db");
						System.out.println("   SQL coonection is seperated.-insert");
					} else {
						System.out.println("   SQL coonection is shared.-insert");
					}

					if(conn.isClosed())
					{
						conn = DriverManager.getConnection("jdbc:sqlite:test.db");
					}
					prep = conn.prepareStatement("insert into people values (?, ?, ?, ?)");

					prep.setString(1, name);
					prep.setString(2, occupation);
					prep.setInt(3, age);
					prep.setInt(4, salary);
					prep.executeUpdate();

					long duration = System.currentTimeMillis() - startTime;
					System.out.println("   SQL Insert completed: " + duration);
				} finally {
					if (prep != null)
						prep.close();
					if (isShareConnection == false && conn != null)
						conn.close();
				}
			} catch (SQLException e) {
				long duration = System.currentTimeMillis() - startTime;
				System.out.print("   SQL Insert failed: " + duration);
				System.out.println(" SQLException: " + e);
			}
		}
	}
}
