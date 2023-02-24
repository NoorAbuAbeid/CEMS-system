package entity;

import java.io.Serializable;

public class Course implements Serializable {

	private static final long serialVersionUID = 1L;
	/** Subject the course belongs to*/
	public String subjectID;
	
	/** the course ID */
	public String courseID;
	
	/** the course name*/
	public String name;
	
	/**
	 * @param subjectID the subject that the course belongs to
	 * @param courseID the ID of the course
	 * @param name the name of the course
	 */
	public Course(String subjectID, String courseID, String name) {
		this.subjectID = subjectID;
		this.courseID = courseID;
		this.name = name;
	}
	
	
	/**
	 * empty constructor
	 */
	public Course()
	{
		
	}

	public String getSubjectID() {
		return subjectID;
	}

	public void setSubjectID(String subjectID) {
		this.subjectID = subjectID;
	}

	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Course [subjectID=" + subjectID + ", courseID=" + courseID + ", name=" + name + "]";
	}
	
	
	
}
