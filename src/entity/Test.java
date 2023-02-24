package entity;

import java.io.Serializable;
import java.util.ArrayList;

/** Test class representing a Test for student built by teachers */
public class Test implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** Test id */
	private String id;
	
	/** Test duration in minutes */
	private int duration;
	
	/** list of questions */
	private ArrayList<Question> questions;
	
	/** pointsPerQuestion[i] represents the question points for
	 *  questions[i] */
	private ArrayList<Integer> pointsPerQuestion;
	
	/** Used by student to take the test*/
	private String executionCode;
	
	/** the author of the test */
	private String teacherName;
	
	private String teacherUsername;
	
	private String teacherNotes;
	
	private String studentNotes;

	
	/**
	 * 
	 * @param id Test id
	 * @param duration Test duration in minutes 
	 * @param questions list of questions in the test
	 * @param pointsPerQuestion pointsPerQuestion[i] represents the 
	 *  question points for questions[i]
	 * @param executionCode Used by students to take the test
	 * @param teacherName the teacher that wrote the test
	 */
	public Test(String id, int duration, ArrayList<Question> questions, 
				ArrayList<Integer> pointsPerQuestion,
				String teacherName, String teacherUsername, 
				String teacherNotes, String studentNotes) {
		this.id = id;
		this.duration = duration;
		this.questions = questions;
		this.pointsPerQuestion = pointsPerQuestion;
		this.teacherName = teacherName;
		this.teacherUsername = teacherUsername;
		this.teacherNotes = teacherNotes;
		this.studentNotes = studentNotes;
	}
	
	/**
	 * Empty Constructor */
	public Test() {
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public String getTeacherNotes() {
		return teacherNotes;
	}

	public void setTeacherNotes(String teacherNotes) {
		this.teacherNotes = teacherNotes;
	}

	public String getStudentNotes() {
		return studentNotes;
	}

	public void setStudentNotes(String studentNotes) {
		this.studentNotes = studentNotes;
	}

	public ArrayList<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}
	public ArrayList<Integer> getPointsPerQuestion() {
		return pointsPerQuestion;
	}
	public void setPointsPerQuestion(ArrayList<Integer> pointsPerQuestion) {
		this.pointsPerQuestion = pointsPerQuestion;
	}
	public String getExecutionCode() {
		return executionCode;
	}
	public void setExecutionCode(String executionCode) {
		this.executionCode = executionCode;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	

	
	public String getTeacherUsername() {
		return teacherUsername;
	}

	public void setTeacherUsername(String teacherUsername) {
		this.teacherUsername = teacherUsername;
	}

	@Override
	public String toString() {
		return "Test [id=" + id + ", duration=" + duration + ", questions=" + questions + ", pointsPerQuestion="
				+ pointsPerQuestion + ", executionCode=" + executionCode + ", teacherName=" + teacherName
				+ ", teacherUsername=" + teacherUsername + ", teacherNotes=" + teacherNotes + ", studentNotes="
				+ studentNotes + "]";
	}

	
	
	
	
	

}