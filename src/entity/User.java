package entity;

import java.io.Serializable;

/**
 * represents a User in the system
 */
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** the user ID */
	private int id;
	
	/** the User's username */
	private String username;
	
	/** the user password */
	private String password;
	
	/**  the user first name*/
	private String firstName;
	
	/** the user last name */
	private String lastName;
	
	/**  the user email*/
	private String email;
	
	/**the user permission */
	private String permissions;
	
	
	/**
	 * @param id the user ID
	 * @param username the user's username
	 * @param password the user password
	 * @param firstName the user first name
	 * @param lastName the user last name
	 * @param email the user email
	 * @param permissions the user permission - teacher/ student/ principal
	 */
	public User(int id, String username, String password, String firstName, String lastName, String email,
			String permissions) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.permissions = permissions;
	}
	
	/**
	 * constructor
	 * @param username username account
	 * @param password password for the username  account
	 */
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPermissions() {
		return permissions;
	}
	public void setPermssions(String permssions) {
		this.permissions = permssions;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", email=" + email + ", permssions=" + permissions + "]";
	}
	
	

}
