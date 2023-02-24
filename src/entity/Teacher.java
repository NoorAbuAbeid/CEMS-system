package entity;

import java.util.ArrayList;

/**
 * a teacher is a type of user
 * every teacher has specific subjects he teaches
 */
public class Teacher extends User {
	/** the teacher ID */
	public int id;
	
	/** a list of subjects the teacher teaches */
	public ArrayList<String> subjects;
	
	/**
	 * @param id the teacher ID
	 * @param username the teacher username
	 * @param password the teacher password
	 * @param firstName the teacher first name
	 * @param lastName the teacher last name
	 * @param email the teacher email address
	 * @param permissions the user type - "teacher"
	 * @param subjects a list of the subject the teacher teach
	 */
	public Teacher(int id, String username, String password, String firstName, String lastName, String email,
			String permissions, ArrayList<String> subjects) {
		super(id, username, password, firstName, lastName, email, permissions);
		this.subjects = subjects;
	}
	
	public ArrayList<String> getSubjects() {
		return subjects;
	}
	public void setSubjects(ArrayList<String> subjects) {
		this.subjects = subjects;
	}
	
	@Override
	public String toString() {
		return "Teacher [id=" + id + ", subjects=" + subjects + "]";
	}
	
	

}
