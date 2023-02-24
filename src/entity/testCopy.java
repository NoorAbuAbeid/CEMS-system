package entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * represents a copy of a test done by a student
 */
public class testCopy  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/** the testID  */
	private String testID;
	
	/** the teacher that did the test */
	private String teacherUsername;
	
	/** the year of the test */
	private String year;
	
	/** the month of the test */
	private String month;
	
	/** the day of the test */
	private String day;
	
	/** the test semester (01 or 02) */
	private String semester;
	
	/** a list of the students answer
	 * array[0] -> answer in question 0(first question) */
	private ArrayList<Integer> studentAnswers;
	
	/** student's final score */
	private int finalScore;
	
	/** actual time of test - including extensions */
	private long actualTime;
	
	/** the student ID */
	private String studentUsername;
	
	/** test approved by teacher or not
	 * cannot see copy until score is approved */
	private String scoreApproved;
	
	/** if finalScore was changed - teacher must provide reasons */
	private String reasons;
	
	/** the teacher that wrote the test*/
	private String testWriterUsername;
	
	/** answers - if test was manual */
	private Object answersManual;

	private String subject;
	
	private String status;
	
	/**
	 * array byte of the manual test to upload.
	 */
	private byte[] arrByteManualTestUpload;

	public testCopy() {
		// TODO Auto-generated constructor stub
	}
	
	

	/**
	 * @param testID the test ID
	 * @param teacherUsername the teacher that did the test
	 * @param year year of test
	 * @param month month of test
	 * @param day day of test
	 * @param semester semester of test (01 or 02)
	 * @param studentAnswers list of students answers
	 * @param finalScore final score in test
	 * @param actualTime the actual time of test- including additions(if any)
	 * @param studentUsername the username of student that did the test
	 * @param scoreApproved "yes" or "no". student can only view approved tests
	 * @param reasons if score was changed - reasons for change
	 * @param testWriterUsername username of teacher that wrote the test
	 * @param answersManual if test was manual - a file with answers
	 */
	public testCopy(String testID, String teacherUsername, String year, String month, String day, String semester,
			ArrayList<Integer> studentAnswers, int finalScore, long actualTime, String studentUsername,
			String scoreApproved, String reasons, String testWriterUsername, Object answersManual, String status) {
		super();
		this.testID = testID;
		this.teacherUsername = teacherUsername;
		this.year = year;
		this.month = month;
		this.day = day;
		this.semester = semester;
		this.studentAnswers = studentAnswers;
		this.finalScore = finalScore;
		this.actualTime = actualTime;
		this.studentUsername = studentUsername;
		this.scoreApproved = scoreApproved;
		this.reasons = reasons;
		this.testWriterUsername = testWriterUsername;
		this.answersManual = answersManual;
		this.status = status;
	}

	

	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getTestID() {
		return testID;
	}

	public void setTestID(String testID) {
		this.testID = testID;
	}

	public String getTeacherUsername() {
		return teacherUsername;
	}

	public void setTeacherUsername(String teacherUsername) {
		this.teacherUsername = teacherUsername;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public ArrayList<Integer> getStudentAnswers() {
		return studentAnswers;
	}

	public void setStudentAnswers(ArrayList<Integer> studentAnswers) {
		this.studentAnswers = studentAnswers;
	}

	public int getFinalScore() {
		return finalScore;
	}

	public void setFinalScore(int finalScore) {
		this.finalScore = finalScore;
	}

	public long getActualTime() {
		return actualTime;
	}

	public void setActualTime(long actualTime) {
		this.actualTime = actualTime;
	}

	public String getStudentUsername() {
		return studentUsername;
	}

	public void setStudentUsername(String studentUsername) {
		this.studentUsername = studentUsername;
	}

	public String isScoreApproved() {
		return scoreApproved;
	}

	public void setScoreApproved(String scoreApproved) {
		this.scoreApproved = scoreApproved;
	}
	
	public String getScoreApproved() {
		return scoreApproved;
	}

	public String getReasons() {
		return reasons;
	}

	public void setReasons(String reasons) {
		this.reasons = reasons;
	}

	public String getTestWriterUsername() {
		return testWriterUsername;
	}

	public void setTestWriterUsername(String testWriterUsername) {
		this.testWriterUsername = testWriterUsername;
	}

	public Object getAnswersManual() {
		return answersManual;
	}

	public void setAnswersManual(Object answersManual) {
		this.answersManual = answersManual;
	}
	
	public void setSubject(String subject)
	{
		this.subject = subject;
	}
	
	public String getSubject()
	{
		return this.subject;
	}
	
	public byte[] getArrByteManualTestUpload() {
		return arrByteManualTestUpload;
	}
	public void setArrByteManualTestUpload(byte[] arrByteManualTestUpload) {
		this.arrByteManualTestUpload = arrByteManualTestUpload;
	}



	@Override
	public String toString() {
		return "testCopy [testID=" + testID + ", teacherUsername=" + teacherUsername + ", year=" + year + ", month="
				+ month + ", day=" + day + ", semester=" + semester + ", studentAnswers=" + studentAnswers
				+ ", finalScore=" + finalScore + ", actualTime=" + actualTime + ", studentUsername=" + studentUsername
				+ ", scoreApproved=" + scoreApproved + ", reasons=" + reasons + ", testWriterUsername="
				+ testWriterUsername + ", answersManual=" + answersManual + "]";
	}
	
	

	
	
}