package server.dbControl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
	
	private String url = "jdbc:mysql://localhost:3306/cems?serverTimezone=IST";
	private String username = "root";
	private String password = "";
	
	public static Connection myConn;
	
	public DBConnector() {
		createConnection();
	}
	
	
	private void createConnection() {
	    try {
	      Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	      System.out.println("Driver definition succeed");
	    } catch (Exception ex) {
	      System.out.println("Driver definition failed");
	    } 
	    try {
	    	myConn = DriverManager.getConnection(url, username, password);
	      System.out.println("SQL connection succeed");
	    } catch (SQLException ex) {
	      System.out.println("SQLException: " + ex.getMessage());
	      System.out.println("SQLState: " + ex.getSQLState());
	      System.out.println("VendorError: " + ex.getErrorCode());
	    } 
	  }
	
	public void closeConnection() {
	    try {
	      if (!myConn.isClosed())
	    	  myConn.close(); 
	    } catch (SQLException e) {
	      e.printStackTrace();
	    } 
	  }
}
