package client.controllers;

import java.util.ArrayList;

import entity.Message;
import entity.MessageType;
import entity.TestDocs;
import javafx.collections.ObservableList;

/** the class deals with getting info and using it to generate reports*/
public class ReportController {
	/** a list of the testDocs from a single subject */
	public static ArrayList<TestDocs> testDocsBySubject;
	
	
	/**get all the testDocs from database that correspond to the subjectID and belong to the username
	 * @param subjectID the subject ID that the testDocs Belong to
	 * @param username the teacher username
	 * @return returns the list of testDocs from a single subject and username
	 */
	public static ArrayList<TestDocs> getAllTestsDocsBySubject(String subjectID, String username) {
		ArrayList<String> arr = new ArrayList<String>();
		arr.add(subjectID);
		arr.add(username);
		Message msg = new Message(MessageType.GetAllTestsDocsBySubject, arr);
		ClientUI.accept(msg);

		return testDocsBySubject;
	}
	
	/**check that the fields the teacher input in screen to see testDocs are valid
	 * @param subject the selected subject
	 * @param course the selected course
	 * @param year the year of tests
	 * @return returns a string representing if all fields are valid
	 * either "valid" or error message
	 */
	public static String validFieldsSeeTestsStatistics(String subject, String course, String year) {
		try {
			Integer.parseInt(year);
		}catch (NumberFormatException e) {
			return "Invalid year";
		}
		if(subject == null)
			return "Choose subject";
		if(course == null)
			return "Choose course";
		return "valid";
	}
}
