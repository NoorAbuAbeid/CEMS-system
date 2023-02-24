package client.controllers;

import java.util.ArrayList;
import java.util.HashMap;

import entity.Course;
import entity.Message;
import entity.MessageType;
import entity.Question;
import entity.QuestionBank;
import entity.Subject;
import entity.Test;
import entity.TestBank;
import entity.testCopy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * deals with getting and inserting info to database regarding the test from
 * teacher perspective and creating / editing / deleting questions
 */
public class TeacherTestController { // ragah evreything

	/** local copy of all tests by specific teacher or subject */
	public static ArrayList<Test> testArr = new ArrayList<>();

	/** local copy of all courses by specific teacher or subject */
	public static ArrayList<Course> courseArr = new ArrayList<>();

	public static testCopy tc = new testCopy();

	public static ArrayList<Subject> subjects;

	/** next Question ID inside specific subject */
	public static int nextQID;

	/** next Test ID inside specific subject and course */
	public static int nextTID;

	/** local copy of a saved subjectID */
	public static String subjectID;

	/** a list of Questions used in creating tests/ editing and viewing */
	public static ArrayList<Question> questionsBySubject;

	/** local copy of specific courseID */
	public static String courseID;

	public static int tCountByBankAndCourse;

	/** hold a specific question for editing proccess */
	public static Question specificQ;

	public static int currentQuestions = 0;

	public static int currentBanks = 0;

	/** local instance of a testID */
	public static String testID;

	public static Test currentTest;

	/** a local copy of the testCopies of a specific year */
	public static ArrayList<testCopy> testCopyList;

	/** used to indicate if the testCopy was approved or not */
	public static boolean approvedScore;
	
	/**
	 * flag is true when the teacher can sent a request for extra time
	 */
	public static boolean flagForRequestValidExecCode=false;
	
	public static String executionCodeForExtraTime=null;

	/**
	 * @param username the teacher username
	 * @return returns a list of all the tests by "username"
	 */
	public static ArrayList<Test> getAllTests(String username) {
		Message msg = new Message(MessageType.GetAllTests, username);
		ClientUI.accept(msg);

		return testArr;
	}

	/**
	 * @param subjectID the subject ID
	 * @return returns a list of all the tests by given subjectID
	 */
	public static ArrayList<Test> getAllTestsBySubject(String subjectID) {
		Message msg = new Message(MessageType.GetAllTestsBySubject, subjectID);
		ClientUI.accept(msg);

		return testArr;
	}

	/**
	 * @param subjectName the subject name
	 * @return return a list of all the course names in given subjectName
	 */
	public static ObservableList<String> getCourseList(String subjectName) {
		String subjectID = getSubjectID(subjectName);

		ObservableList<String> arr = FXCollections.observableArrayList();
		Message msg = new Message(MessageType.getCoursesBySubject, subjectID);
		ClientUI.accept(msg);

		for (Course c : courseArr)
			arr.add(c.getName());
		return arr;
	}

	/**
	 * @param q questioin to add to database
	 */
	public static void addQuestion(Question q) {
		Message msg = new Message(MessageType.addQuestion, q);
		ClientUI.accept(msg);
	}

	/**
	 * @param subjectID  the subjectID
	 * @param courseName the course name
	 * @return returns the courseID by given subjectID and coursename from local
	 *         copy of courses -- faster access
	 */
	public static String getCourseIDNotDB(String subjectID, String courseName) {
		for (Course c : courseArr) {
			if (c.getSubjectID().equals(subjectID) && c.getName().equals(courseName))
				return c.getCourseID();
		}
		return null;
	}

	/**
	 * @param username the teacher username
	 * @return returns a list of subjects the teacher teach
	 */
	public static ObservableList<String> getAllSubjects(String username) {
		Message msg = new Message(MessageType.GetAllSubjects, username);
		ClientUI.accept(msg);
		ObservableList<String> answer = FXCollections.observableArrayList();
		for (Subject s : subjects)
			answer.add(s.getName());
		return answer;
	}

	/**
	 * lock a given test
	 * 
	 * @param test the test to lock
	 */
	public static void lockTest(Test test) {
		Message msg = new Message(MessageType.LockTest, test);
		ClientUI.accept(msg);

	}

	/**
	 * request extra time for test
	 * 
	 * @param test the test that needs extra time
	 */
	public static void requestExtraTime(testCopy test) {
		Message msg = new Message(MessageType.RequestExtraTime, test);
		ClientUI.accept(msg);

	}

	public static Test getTestQuestions(String code) {

		Message msg = new Message(MessageType.GetTestQuestions, code);

		ClientUI.accept(msg);

		return currentTest;
	}

	/**
	 * @param subjectID
	 * @param username
	 * @return return next QuestionID by given subjectID and teacherUsername
	 */
	public static int getNextQID(String subjectID, String username) {
		ArrayList<String> arr = new ArrayList<>();
		arr.add(subjectID);
		arr.add(username);
		Message msg = new Message(MessageType.GetNextQID, arr);
		ClientUI.accept(msg);
		return nextQID;
	}

	/**
	 * @param bankName the subject name
	 * @return return the subjectID
	 */
	public static String getSubjectID(String bankName) {
		Message msg = new Message(MessageType.GetSubjectID, bankName);
		ClientUI.accept(msg);
		return subjectID;

	}

	/**
	 * @param subjectID
	 * @param username
	 * @return return a list of questions in specific subject that belong to
	 *         specific teacher
	 */
	public static ObservableList<Question> getQuestionsBySubject(String subjectID, String username) {
		ArrayList<String> arr = new ArrayList<>();
		arr.add(subjectID);
		arr.add(username);
		Message msg = new Message(MessageType.GetQuestionsBySubject, arr);
		ClientUI.accept(msg);

		ObservableList<Question> qList = FXCollections.observableArrayList(questionsBySubject);
		return qList;

	}

	// before add question to question list in test check if question exist and
	// teacher input valid score
	/**
	 * @param questionID      the question ID
	 * @param score           the score i want for the question
	 * @param currentSum      the cuurent sum of questions
	 * @param questionsInTest the other questions
	 * @return return if the input for adding a question to a created test is valid
	 */
	public static String checkValidQuestionID(String questionID, String score, int currentSum,
			ObservableList<String> questionsInTest) {
		boolean questionExist = false, scoreValid = false;
		int scoreNum = 0;

		// check that teacher input a valid questionID from the list of
		// possible questions
		for (Question q : questionsBySubject) {
			if (q.getId().equals(questionID))
				questionExist = true;
		}
		// check if question already added to test
		for (String s : questionsInTest) {
			if (s.equals(questionID))
				return "question already in test";
		}
		// check score input is a number
		try {
			scoreNum = Integer.parseInt(score);
		} catch (NumberFormatException e) {
			e.getStackTrace();
			return "Score must be a number";
		}
		// check total sum not over 100
		if (currentSum + scoreNum > 100)
			return "total points sum > 100";
		// check score is between 1 and 100
		else if (scoreNum < 1 || scoreNum > 100)
			return "Score must be between 1 and 100";
		else
			scoreValid = true;
		if (scoreValid && questionExist)
			return "valid";
		return "not valid";
	}

	/**
	 * @param subject
	 * @param courseName
	 * @return return a courseID by given subject and courseName
	 */
	public static String getCourseID(String subject, String courseName) {
		ArrayList<String> arr = new ArrayList<>();
		arr.add(subject);
		arr.add(courseName);
		Message msg = new Message(MessageType.GetCourseID, arr);
		ClientUI.accept(msg);
		return courseID;
	}

	/**
	 * used for editing questions
	 * 
	 * @param id       questionID
	 * @param username the teacher username
	 * @return returns a specific question by ID and teacherUsername
	 */
	public static Question getQuestionByID(String id, String username) {
		ArrayList<String> arr = new ArrayList<>();
		arr.add(id);
		arr.add(username);
		Message msg = new Message(MessageType.GetQuestionByID, arr);
		ClientUI.accept(msg);
		return specificQ;
	}

	/**
	 * @param q             the question
	 * @param subject
	 * @param correctAnswer
	 * @return return valid or not for fields in creating a question
	 */
	public static String isValidFieldsCreateQuestion(Question q, String subject, String correctAnswer) {
		if (subject == null)
			return "please choose subject";
		else if (q.getAnswers().get(0).equals("") || q.getAnswers().get(1).equals("")
				|| q.getAnswers().get(2).equals("") || q.getAnswers().get(3).equals(""))
			return "please write answers";
		else if (correctAnswer == null)
			return "please choose correct answer";
		else if (q.getDescription().equals(""))
			return "please enter description";
		return "valid";
	}

	/**
	 * @param t        the test to create
	 * @param subject  the test subject
	 * @param course   the test course
	 * @param duration the test duration
	 * @return return valid or error message about fields in form of creating
	 *         question
	 */
	public static String isValidFieldsCreateTest(Test t, String subject, String course, String duration) {
		if (subject == null)
			return "please choose subject";
		else if (course == null)
			return "please choose course";
		else if (duration.equals("") || duration == null)
			return "please enter duration";
		else if (t.getPointsPerQuestion().size() == 0)
			return "did not set points for questions";
		else if (t.getQuestions().size() == 0)
			return "did not set questions for test";
		return "valid";
	}

	/**
	 * @param teacherUsername the teacher username
	 * @param subject         the question subject
	 * @return return the next QuestionID when creating question
	 */
	public static String getNextQuestionID(String teacherUsername, String subject) {
		String subjectID = getSubjectID(subject);
		int nextID = getNextQID(subjectID, teacherUsername);
		if (nextID < 10)
			return (subjectID + "00" + nextID);
		else if (nextID <= 99)
			return (subjectID + "0" + nextID);
		else if (nextID <= 999)
			return (subjectID + nextID);
		else {
			return "error";
		}
	}

	/**
	 * @param subjectID       the subjectID
	 * @param courseID        the courseID
	 * @param teacherUsername the teacher username
	 * @return return the next TestID when creating tests
	 */
	public static String getNextTestID(String subjectID, String courseID, String teacherUsername) {
		int nextID = getNextTID(subjectID, courseID, teacherUsername);
		if (nextID < 10)
			return (subjectID + courseID + "0" + nextID);
		else if (nextID <= 99)
			return (subjectID + courseID + nextID);
		else
			return "error";
	}

	/**
	 * @param subjectID
	 * @param courseID
	 * @param teacherUsername
	 * @return return the next testID in given subjectID, courseID and
	 *         teacherUsername
	 */
	private static int getNextTID(String subjectID, String courseID, String teacherUsername) {
		ArrayList<String> arr = new ArrayList<>();
		arr.add(subjectID);
		arr.add(courseID);
		arr.add(teacherUsername);
		Message msg = new Message(MessageType.GetNextTID, arr);
		ClientUI.accept(msg);
		return nextTID;
	}

	/**
	 * @param code the executionCode
	 * @return get testID by given executionCode
	 */
	public static String getTestID(String code) {
		Message msg = new Message(MessageType.GetTestCode, code);
		ClientUI.accept(msg);

		return testID;
	}

	public static ObservableList<testCopy> getTestCopyList(String year, String username) {
		ArrayList<String> arr = new ArrayList();
		arr.add(year);
		arr.add(UserController.username);

		Message msg = new Message(MessageType.getTestsCopyByYear, arr);
		ClientUI.accept(msg);

		return FXCollections.observableArrayList(testCopyList);
	}

	public static String validYearTeacherCheckTestScore(String year) {
		int yearInt;
		try {
			yearInt = Integer.parseInt(year);
			return "valid";
		} catch (Exception e) {
			return "Not valid";
		}
	}

	public static String validNewScoreCheckTestScore(String score, String reasons, testCopy row) {
		if (row == null)
			return "choose a test copy";
		if (reasons.equals(""))
			return "must add reasons to change the score";
		try {
			int x = Integer.parseInt(score);
			if (x < 0 || x > 100)
				return "invalid score";
		} catch (Exception e) {
			return "invalid score";
		}
		return "valid";
	}

}
