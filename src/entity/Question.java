package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/** A question that can be used in a test */
public class Question implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** The Question ID */
	private String id;
	
	/** The question description(the question itself) */
	private String description;
	
	/** List of possible answers */
	private ArrayList<String> answers;
	
	/** The correct answer to the current question */
	private int correctAnswer;
	
	/** The teacher that wrote the question */
	private String teacherName;
	
	private String teacherUsername;
	
	/**
	 * @param id The unique question ID
	 * @param description The text of the question
	 * @param answers List of possible answers
	 * @param correctAnswer The correct answer
	 * @param teacherName The teacher who wrote the test
	 */
	public Question(String id, String description, ArrayList<String> answers,
			int correctAnswer, String teacherName, String teacherUsername) {
		this.id = id;
		this.description = description;
		this.answers = answers;
		this.correctAnswer = correctAnswer;
		this.teacherName = teacherName;
		this.teacherUsername = teacherUsername;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getTeacherUsername() {
		return teacherUsername;
	}
	public void setTeacherUsername(String teacherUsername) {
		this.teacherUsername = teacherUsername;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ArrayList<String> getAnswers() {
		return answers;
	}
	public void setAnswers(ArrayList<String> answers) {
		this.answers = answers;
	}
	public int getCorrectAnswer() {
		return correctAnswer;
	}
	public void setCorrectAnswer(int correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	@Override
	public String toString() {
		return "Question [id=" + id + ", description=" + description + ", answers=" + answers + ", correctAnswer="
				+ correctAnswer + ", teacherName=" + teacherName + ", teacherUsername=" + teacherUsername + "]";
	}
	
	
	

}