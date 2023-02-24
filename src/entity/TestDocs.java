package entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * representing documentation of a given test(among all students of test)
 */
public class TestDocs implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** the test ID*/
	private String id;
	
	/**the year of the test */
	private String year;
	
	/**  the test semester*/
	private String semester;
	
	/** test duration*/
	private String assignedTime;
	
	/** number of students that started the test*/
	private int numStudentsStart;
	
	/**  number of students that did not submit on time*/
	private int numStudentsNotFinishInTime;
	
	/** number of students that submit on time */
	private int numStudentsFinishInTime;
	
	/** the date of the test */
	private String date;
	
	/**  the test average*/
	private double average;
	
	/**  the test median*/
	private double median;
	
	/**  a distribution of grades into deciles*/
	private ArrayList<Integer> distribution;
	
	/** the teacher that wrote the test*/
	private String teacherUsername;
	
	
	/**
	 * @param id the test ID
	 * @param year the test year
	 * @param semester the test semester
	 * @param assignedTime the test duration
	 * @param numStudentsStart number of students that started
	 * @param numStudentsNotFinishInTime number of students that did not submit on time
	 * @param numStudentsFinishInTime number of students that submit on time
	 * @param date the test date
	 * @param average the test average
	 * @param median the test median
	 * @param distribution a distribution of grades into deciles
	 * @param teacherUsername the teacher who wrote the test
	 */
	public TestDocs(String id, String year, String semester, String assignedTime, int numStudentsStart,
			int numStudentsNotFinishInTime, int numStudentsFinishInTime, String date, double average, double median,
			ArrayList<Integer> distribution, String teacherUsername) {
		super();
		this.id = id;
		this.year = year;
		this.semester = semester;
		this.assignedTime = assignedTime;
		this.numStudentsStart = numStudentsStart;
		this.numStudentsNotFinishInTime = numStudentsNotFinishInTime;
		this.numStudentsFinishInTime = numStudentsFinishInTime;
		this.date = date;
		this.average = average;
		this.median = median;
		this.distribution = distribution;
		this.teacherUsername = teacherUsername;
	}

	public TestDocs() {
		// TODO Auto-generated constructor stub
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

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getAssignedTime() {
		return assignedTime;
	}

	public void setAssignedTime(String assignedTime) {
		this.assignedTime = assignedTime;
	}

	public int getNumStudentsStart() {
		return numStudentsStart;
	}

	public void setNumStudentsStart(int numStudentsStart) {
		this.numStudentsStart = numStudentsStart;
	}

	public int getNumStudentsNotFinishInTime() {
		return numStudentsNotFinishInTime;
	}

	public void setNumStudentsNotFinishInTime(int numStudentsNotFinishInTime) {
		this.numStudentsNotFinishInTime = numStudentsNotFinishInTime;
	}

	public int getNumStudentsFinishInTime() {
		return numStudentsFinishInTime;
	}

	public void setNumStudentsFinishInTime(int numStudentsFinishInTime) {
		this.numStudentsFinishInTime = numStudentsFinishInTime;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}

	public double getMedian() {
		return median;
	}

	public void setMedian(double median) {
		this.median = median;
	}

	public ArrayList<Integer> getDistribution() {
		return distribution;
	}

	public void setDistribution(ArrayList<Integer> distribution) {
		this.distribution = distribution;
	}

	@Override
	public String toString() {
		return "TestDocs [id=" + id + ", year=" + year + ", semester=" + semester + ", assignedTime=" + assignedTime
				+ ", numStudentsStart=" + numStudentsStart + ", numStudentsNotFinishInTime="
				+ numStudentsNotFinishInTime + ", numStudentsFinishInTime=" + numStudentsFinishInTime + ", date=" + date
				+ ", average=" + average + ", median=" + median + ", distribution=" + distribution + "]";
	}
	
	
}
