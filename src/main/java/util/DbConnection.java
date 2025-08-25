package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
	
	public static Connection getDb()
	{
		String url = "jdbc:mysql://localhost:3306/music_player";
		String user = "root";
		String password= "1234";
		Connection conn =null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return conn;
		
		
		
	}
	
	
	
	
	
}
