package server.dbControl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import entity.User;

public class UserDBController {

	/**
	 * the array saves the users that currently connected
	 */
	public static ArrayList<String> usersConnected = new ArrayList<String>();
	//need to add func for delete users that had been logout!!
	
	/**
	 * stores the data from the database
	 */
	static ResultSet rs;

	/**
	 *  get details from the data base, and check if the user logged in successfully
	 * @param userInfo
	 * @return the condition of the user after he tried to login 
	 */
	public static String tryToConnect(User userInfo){
		String username = userInfo.getUsername();
		String password = userInfo.getPassword();
		String sqlQuery = "select username,password,permission from user";
		try {
			if (DBConnector.myConn != null) {
				Statement st = DBConnector.myConn.createStatement();
				rs = st.executeQuery(sqlQuery);
				
				boolean checkFieldsNotEmpty = checkFieldsNotEmpty(username,password);
				if(!checkFieldsNotEmpty) { rs.close(); return "You must fill all the fields";}
				
				
				boolean checkIfUserExist = checkIfUserExist(username);
				if(!checkIfUserExist) { rs.close(); return "The user is not exist";} 
				
				boolean checkValidInfo = checkValidInfo(username,password);
				if(!checkValidInfo) { rs.close(); return "The password is incorrect";} 
				
				boolean userNotConnected = userNotConnected(username);
				if(!userNotConnected) { rs.close(); return "The user is already connected";}
				
				addUserToUsersConnectedArrayList(username);

				String checkPermission = checkPermission(username);
				return checkPermission;
		
			} else
				System.out.println("myConn is NULL !");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return "ERROR-LOGIN";
	}

	/**
	 * check if all the fields are filled
	 * @param username is the name of the user
	 * @param password of the user
	 * @return true in case that all the fields are not empty otherwise false
	 */
	private static boolean checkFieldsNotEmpty(String username, String password) {
		if( username.isEmpty()  || password.isEmpty())
			return false;
		return true;
	}

	/**
	 * add the user to the arrayList usersconnected
	 * @param username name of the user account
	 */
	private static void addUserToUsersConnectedArrayList(String username) {
		usersConnected.add(username);
	}

	/**
	 * check the existence of the user
	 * 
	 * @param username is the name of the user
	 * @return true if exist in data base otherwise false
	 */
	public static boolean checkIfUserExist(String username) {
		try {
			rs.beforeFirst();
				while (rs.next()) {
					// search for the user in the query
					if (username.equals(rs.getString(1)))
						return true;
				}
			//	rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	/**
	 * check if username and password match to the username and password
	 *  in the database
	 * 
	 * @param userInfo - gets the username and password
	 * @return
	 */
	public static boolean checkValidInfo(String username,String password) {
		try {
			rs.beforeFirst();
				while (rs.next()) {
					// search for the user in the rs
					if (username.equals(rs.getString(1)) && password.equals(rs.getString(2)))
						return true;
				}
				//rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * check if the user is already connected
	 * 
	 * @param username name of the user account
	 * @return true if user is not connected otherwise false
	 */
	private static boolean userNotConnected(String username) { // olay ipol biglal marachNULL
		
		 for(String name:usersConnected) {
			 if(name.equals(username))
				 return false;
		 }
		return true;
	}

	/**
	 * gets the permission of the account according to user name
	 * 
	 * @param username - name of the user account
	 * @return
	 */
	public static String checkPermission(String username) {
	try {
			rs.beforeFirst();
			while (rs.next()) {
				// search for the user in the query
				if (username.equals(rs.getString(1)))
					return rs.getString(3); // return permission
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	  return "ERROR-Permission";
	}

	public static String getName(String username) {
		String sqlQuesry = "select * from user where username = \"" + username + "\";";
		Statement st;
		try {
			st = DBConnector.myConn.createStatement();
			ResultSet rs = st.executeQuery(sqlQuesry);
			rs.next();
			return rs.getString(4) + " " + rs.getString(5);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	/**
	 * remove user from current users connected
	 * @param username username of the user
	 */
	public static void removeUserFromLoginArr(String username) {
		usersConnected.remove(username);
	}

}
