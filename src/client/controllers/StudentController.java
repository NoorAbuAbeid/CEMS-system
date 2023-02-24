package client.controllers;

import java.util.ArrayList;
import java.util.List;

import entity.Message;
import entity.MessageType;
import entity.Test;
import entity.TestDocs;
import entity.testCopy;
import ocsf.server.ConnectionToClient;
import server.dbControl.StudentDBController;

public class StudentController {

	public static boolean testExist;
	public static boolean studentIDExist;
	public static boolean validCode;
	public static ArrayList<String> examDate;
	public static int numberOfStudentsStartedExam;
	public static int numberOfStudentsFinishedExam;
	public static ArrayList<Integer> studentGrades;
	public static boolean lastStudent;
	public static ArrayList<testCopy> studentTestCopy;
	public static String subjectName;
	public static Test testPreview;
	
	/**
	 * if theacher locked the test so flag is true
	 */
	public static boolean flagForLockTest=false;
	
	/**
	 * the client's that doing the test
	 */
	public static List<ConnectionToClient> onGoingTestStudents;
	
	public static boolean isTestExist(String testID)
	{
		Message msg = new Message(MessageType.CheckTest, testID);
		ClientUI.accept(msg);
		return testExist;
	}
	
	public static boolean isStudentIDExist(String studentID)
	{
		Message msg = new Message(MessageType.CheckStudentID, studentID);
		ClientUI.accept(msg);
		return studentIDExist;
	}

	public static boolean isExecutionCodeValid(String code) {
		
		Message msg = new Message(MessageType.CheckValidCode, code);
		ClientUI.accept(msg);
		return validCode;
	}

	public static void submitTest(testCopy tc)
	{
		Message msg = new Message(MessageType.SubmitTest, tc);
		ClientUI.accept(msg);
		return;
	}

	public static void AddStudentToOnGoing(ArrayList<String> studentDetails) {
		
		Message msg = new Message(MessageType.AddStudentToOnGoingOnline, studentDetails);
		ClientUI.accept(msg);
		return;
		
	}

	public static void removeStudentFromOnGoing(ArrayList<String> studentDetails)
	{
		Message msg = new Message(MessageType.RemoveStudentFromOnGoingOnline, studentDetails);
		ClientUI.accept(msg);
		return;
		
	}

	public static ArrayList<String> getExamDate(String executionCode) {
		Message msg = new Message(MessageType.GetExamDate, executionCode);
		ClientUI.accept(msg);
		return examDate;
	}

	public static void submitFailedTest(testCopy failed) {
		Message msg = new Message(MessageType.SubmitFailedTest, failed);
		ClientUI.accept(msg);
		return;	
	}
	
	public static int getNumberOfStudentsStartedExam(String id)
	{
		Message msg = new Message(MessageType.GetNumberOfStudentsStartedExam, id);
		ClientUI.accept(msg);
		return numberOfStudentsStartedExam;
	}
	
	public static int getNumberOfStudentsFinishedExam(String id)
	{
		Message msg = new Message(MessageType.GetNumberOfStudentsFinishedExam, id);
		ClientUI.accept(msg);
		return numberOfStudentsFinishedExam;
	}
	
	public static ArrayList<Integer> getStudentGrades(String id)
	{
		Message msg = new Message(MessageType.GetStudentGrades, id);
		ClientUI.accept(msg);
		return studentGrades;
	}
	
	public static void insertToTestDocs(TestDocs td)
	{
		Message msg = new Message(MessageType.InsertToTestDocs, td);
		ClientUI.accept(msg);
	}

	public static void removeTestFromPlannedTest(String executionCode) {
		Message msg = new Message(MessageType.RemoveTestFromPlanned, executionCode);
		ClientUI.accept(msg);
		
	}

	public static void updateTestCodeToNull(String id) {
		Message msg = new Message(MessageType.UpdateTestCodeToNull, id);
		ClientUI.accept(msg);
		
	}

	public static boolean checkIfLastStudent(String code) {
		Message msg = new Message(MessageType.CheckLastStudent,code );
		ClientUI.accept(msg);
		return lastStudent;
	}
	
	public static ArrayList<testCopy> getAllStudentDetails(String username)
	{
		
		Message msg = new Message(MessageType.GetStudentDetails,username);
		ClientUI.accept(msg);
		return studentTestCopy;
	}
	
	public static String getSubjectNamebyID(String id)
	{
		Message msg = new Message(MessageType.GetSubjectNamebyID,id);
		ClientUI.accept(msg);	
		return subjectName;
	}

	public static Test previewTest(String testID) {
		Message msg = new Message(MessageType.PreviewTest,testID);
		ClientUI.accept(msg);
		return testPreview;
	}
	
	public static void submitTestManual(testCopy tc) {
		Message msg = new Message(MessageType.SubmitTestManual, tc);
		ClientUI.accept(msg);
		return;
	}

public static void AddStudentToOnGoingManual(ArrayList<String> studentDetails) {
		
		Message msg = new Message(MessageType.AddStudentToOnGoing, studentDetails);
		ClientUI.accept(msg);
		return;

	}
	
}//End StudentController
