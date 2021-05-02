package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection implements SqliteDB{

	
	static Connection conn;

	private DBConnection() {
		
	}
	
	public static Connection getDBConnection() {
		synchronized(DBConnection.class) {
			if(conn == null) {
				try {
					conn = DriverManager.getConnection(URL);
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			}
		}
		return conn;
	}
	
	public static void closeConnection() {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
		}
	}
}
