package server.controllers;


import java.io.BufferedInputStream;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.sql.Blob;

import client.controllers.ClientUI;
import client.controllers.StudentController;
import client.controllers.UserController;
import client.gui.CreateQuestionController;
import entity.Course;
import entity.Message;
import entity.MessageType;
import entity.Question;
import entity.QuestionBank;
import entity.RequestExtraTime;
import entity.Subject;
import entity.Test;

import entity.User;
import entity.testCopy;
import entity.TestBank;
import entity.TestDocs;
import javafx.collections.ObservableList;
import ocsf.server.*;
import server.dbControl.*;

public class ServerController extends AbstractServer {

	private static Question q;
	public String clientIp;
	public String hostName;
	public String clientConnected = "Not Connected";
	public static DBConnector dbConnector;
	Message msgFromServer = null;
	boolean specificMsg = false;
	String temp;

	public ServerController(int port) {
		super(port);
		dbConnector = new DBConnector();
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		Message message = (Message) msg;
		boolean check;
		switch (message.getMessageType()) {
		case Hello:
			getName((String)message.getMessageData());
			break;
		case GetAllSubjects:
			ArrayList<Subject> subjects = QuestionDBController.getAllSubjects((String)message.getMessageData());
			msgFromServer = new Message(MessageType.GetAllSubjects, subjects);
			break;
		case GetAllTests:
			getAllTests((String)message.getMessageData());
			break;
		case GetAllTestsBySubject:
			getAllTestsBySubject((String)message.getMessageData());
			break;
		case GetAllTestsDocsBySubject:
			getAllTestsDocsBySubject((ArrayList<String>)message.getMessageData());
			break;
		case getTestsCopyByYear:
			getTestsCopyByYear((ArrayList<String>)message.getMessageData());
			break;
		case getCoursesBySubject:
			getCoursesBySubject((String)message.getMessageData());
			break;
		case GetNextQID:
			getNextQID((ArrayList<String>)message.getMessageData());
			break;
		case GetNextTID:
			getNextTID((ArrayList<String>)message.getMessageData());
			break;
		case addQuestion:
			check = QuestionDBController.addQuestion((Question) message.getMessageData());
			msgFromServer = new Message(MessageType.addQuestion, check);
			break;
		case DeleteQuestion:
			check = QuestionDBController.deleteQuestion((Question)message.getMessageData());
			msgFromServer = new Message(MessageType.DeleteQuestion, check);
			break;
		case deleteTest:
			System.out.println("recieved msg: serverController: test is: " + (Test)message.getMessageData());
			check = TeacherTestDBController.deleteTest((Test)message.getMessageData());
			msgFromServer = new Message(MessageType.deleteTest, check);
			break;
		case ApproveTestCopy:
			 check = TeacherTestDBController.approveTestCopy((ArrayList<String>)message.getMessageData());
			 msgFromServer = new Message(MessageType.ApproveTestCopy, check);
			break;
		case getManualTestDetails:
			ArrayList<String> manualTestDetails = 
			StudentDBController.getManualTestInfo((String)message.getMessageData());
			System.out.println(manualTestDetails.toString()+"In SERVER CONTROLLER");
			 msgFromServer = 
					new Message(MessageType.getManualTestDetails,manualTestDetails);
			break;
		case UpdateTestCopy:
			check = TeacherTestDBController.updateTestCopy((ArrayList<String>)message.getMessageData());
			msgFromServer = new Message(MessageType.UpdateTestCopy, check);
			break;
		case updateTest:
			check = TeacherTestDBController.updateTest((Test)message.getMessageData());
			msgFromServer = new Message(MessageType.deleteTest, check);
			break;
		case GetQuestionByID:
			Question q = QuestionDBController.getQuestionByID((ArrayList<String>)message.getMessageData());
			msgFromServer = new Message(MessageType.GetQuestionByID, q);
			break;
		case AddTest:
			boolean temp = TeacherTestDBController.addTest((Test)message.getMessageData());
			msgFromServer = new Message(MessageType.AddTest, temp);
			break;
		case UpdateQuestion:
			check = QuestionDBController.updateQuestion((Question)message.getMessageData());
			msgFromServer = new Message(MessageType.UpdateQuestion, check);
			break;

		case GetSubjectID:
			getSubjectID((String)message.getMessageData());
			break;
		case GetCourseID:
			getCourseID((ArrayList<String>)message.getMessageData());
			break;
		case GetQuestionsBySubject:
			getQuestionsBySubject((ArrayList<String>)message.getMessageData());
			break;
		case logIn:
			String logInStatus = UserDBController.tryToConnect((User) message.getMessageData());
			msgFromServer = new Message(MessageType.logIn, logInStatus);
			break;
		/*case LockTest:
			//TeacherTestDBController.lockTest((Test)message.getMessageData());
			lockTest(TeacherTestDBController.lockTestDin((String)message.getMessageData()));
			specificMsg = true;
			break;*/

		case CheckTest:
			boolean flag = StudentDBController.checkTest((String)message.getMessageData());
			msgFromServer = new Message(MessageType.CheckedTest,flag);
			break;
		case CheckStudentID:
			boolean isStudentIDExist = StudentDBController.checkStudentID((String)message.getMessageData());
			msgFromServer = new Message(MessageType.CheckedStudentID,isStudentIDExist);
			break;
		case CheckValidCode:
			String testId = StudentDBController.checkValidCode((String)message.getMessageData());
			msgFromServer = new Message(MessageType.CheckedCode,testId);
			break;
		case execCode:
			String id = TestDBController.FindTestIdAccordingToExecCode((String)message.getMessageData());
			 msgFromServer = new Message(MessageType.execCode, id);
			break;
		case downloadManualTest:
			byte[] byteManualTest = TestDBController.getTest((String)message.getMessageData()); 
			 msgFromServer = new Message(MessageType.downloadManualTest, byteManualTest); 
			 break;
		case submitManualTest:
			boolean saveTestInDB = TestDBController.SaveManualTest((byte[])message.getMessageData());
			if(saveTestInDB) { msgFromServer = new Message(MessageType.submitManualTest, "Successfully submitted");}
			else { msgFromServer = new Message(MessageType.submitManualTest, "Error in submit the test in to the database");}

			break;
			
		case SubmitTestManual:
			boolean flagForSubmittedTestSuccessfully = StudentDBController.submitTestManual((testCopy)message.getMessageData());
			msgFromServer = new Message(MessageType.SubmittedTestManual, flagForSubmittedTestSuccessfully);
			break;
			
		case ContinuePlanTest:
			boolean ValidIdAndUsername = TeacherTestDBController.checkValidIdAndUsernameTest((ArrayList<String>)message.getMessageData());
			if(ValidIdAndUsername) { msgFromServer = new Message(MessageType.ContinuePlanTest, "TestID and username matched");}
			else { msgFromServer = new Message(MessageType.ContinuePlanTest, "TestID and username not matched");}
			break;
			
		case InsertPlanTest:
			String insertPlanToDb = TeacherTestDBController.insertPlanTestToDB((ArrayList<String>)message.getMessageData());
			msgFromServer = new Message(MessageType.InsertPlanTest,insertPlanToDb);
			break;
			
			//RAGAH - en bdika she ze takin.ok? 
			//add to HAShMAP ConnectionClient. Shr
		case AddStudentToOnGoing:
			TestDBController.addConnectionClientToHashMap
			(client, ((ArrayList<String>) message.getMessageData()).get(0) );// client, execCode
			TestDBController.addStudentToOnGoing
			((ArrayList<String>)message.getMessageData());
			break;
		case RemoveStudentFromOnGoing:
			TestDBController.removeConnectionClientFromHashMap
			(((ArrayList<String>)message.getMessageData()).get(0),client);
			TestDBController.removeStudentFromOnGoing((ArrayList<String>)message.getMessageData());
			break;
			
		case lockTest:
			//getAndSendToStudentsOnGoingTest(message,client);
			System.out.println("getAndSend  ServerController");
		List<ConnectionToClient> getListOfConnClientGoingTest = 
		TestDBController.lockTest((String)message.getMessageData());
	
	//	System.out.println("is null");
	//	System.out.println("connections: " + getListOfConnClientGoingTest);
	//	System.out.println("not null");
	
		if(getListOfConnClientGoingTest!=null) {
		//	System.out.println("getList Not NULLLLL");
		if(!getListOfConnClientGoingTest.isEmpty()) {
			//System.out.println("Yesh ANashimm ba MIVHAN NOT EMPTY");
	for(ConnectionToClient conToClient : getListOfConnClientGoingTest ) {
			
				Message msgFromServer = new Message
						(MessageType.lockTest,null);
				System.out.println("before send to client: " +conToClient);
				try {
					conToClient.sendToClient(msgFromServer);
				System.out.println("after send to client");
			
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}}}
		Message msgFromServer3= new Message(MessageType.lockTestTeacher,null);
		try {
			client.sendToClient(msgFromServer3);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
			return;
		case DetailsExtraTime: //zarih leshanot et a shem
			//getAndSendToStudentsOnGoingTest(message,client);
			System.out.println("DetailsExtratime ServerController");
			String execCode = 
					((ArrayList<String>)message.getMessageData()).get(0);
			String time =((ArrayList<String>)message.getMessageData()).get(1);

		List<ConnectionToClient> getListOfConnClientGoingTest2 = 
		TestDBController.lockTest(execCode);///////////////////////////////////////
		
	//	System.out.println("is null");
	//	System.out.println("connections: " + getListOfConnClientGoingTest);
	//	System.out.println("not null");
	
		if(getListOfConnClientGoingTest2!=null) {
		//	System.out.println("getList Not NULLLLL");
		if(!getListOfConnClientGoingTest2.isEmpty()) {
			//System.out.println("Yesh ANashimm ba MIVHAN NOT EMPTY");
	for(ConnectionToClient conToClient : getListOfConnClientGoingTest2 ) {
			
				Message msgFromServer = new Message
						(MessageType.addExtraTime,time);
				System.out.println("before send to client: " +conToClient);
				try {
					conToClient.sendToClient(msgFromServer);
				System.out.println("after send to client");
			
				} catch (IOException e) {
					e.printStackTrace();
				}}}}
		Message msgFromServer2= new Message(MessageType.addExtraTimePrinciple,null);
		try {
			client.sendToClient(msgFromServer2);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
			return;
		case AddExecCodeToTestDB:
			TestDBController.addExecCodetoTestDB((ArrayList<String>)message.getMessageData());
			break;
			
			
			/// ragah
			
		case GetTestQuestions:
			Test test = QuestionDBController.getTestQuestions((String)message.getMessageData());
			msgFromServer = new Message(MessageType.TestQuestions, test);
			break;
		case SubmitTest:
			StudentDBController.submitTest((testCopy)message.getMessageData());
			msgFromServer = new Message(MessageType.SubmittedTest, null);
			break;
		case AddStudentToOnGoingOnline:
			StudentDBController.addStudentToOnGoing((ArrayList<String>)message.getMessageData());
			break;
		case RemoveStudentFromOnGoingOnline:
			StudentDBController.removeStudentFromOnGoing((ArrayList<String>)message.getMessageData());
			break;

		case GetExamDate:
			ArrayList<String> examDate = new ArrayList<>();
			examDate = StudentDBController.getExamDate((String)message.getMessageData());
			msgFromServer = new Message(MessageType.GotExamDate, examDate);
			break;
		case GetNumberOfStudentsStartedExam:
			int numberOfStudents = StudentDBController.getNumberOfStudentsStartedExam((String)message.getMessageData());
			msgFromServer = new Message(MessageType.CountedStudents, numberOfStudents);
			break;
		case GetNumberOfStudentsFinishedExam:
			int studentsFinished = StudentDBController.getNumOfStudentsFinishedExam((String)message.getMessageData());
			msgFromServer = new Message(MessageType.CountedStudentsFinished, studentsFinished);
			break;
		case GetStudentGrades:
			ArrayList<Integer> grades = new ArrayList<>();
			grades = StudentDBController.getGrades((String)message.getMessageData());
			msgFromServer = new Message(MessageType.GotGrades, grades);
			break;
		case InsertToTestDocs:
			StudentDBController.insertToTestDocs((TestDocs)message.getMessageData());
			break;
		case RemoveTestFromPlanned:
			StudentDBController.removeTestFromPlanned((String)message.getMessageData());
			break;
		case UpdateTestCodeToNull:
			StudentDBController.updateTestCodeToNull((String)message.getMessageData());
			break;
		case SubmitFailedTest:
			StudentDBController.submitFailedTest((testCopy)message.getMessageData());
			break;
		case CheckLastStudent:
			int isLastStudent = StudentDBController.checkLastStudent((String)message.getMessageData());
			msgFromServer = new Message(MessageType.CheckedIfLast, isLastStudent);
			break;
		case GetStudentDetails:
			
			ArrayList<testCopy> tc = new ArrayList<>();
			tc = StudentDBController.getStudentTestDetails((String)message.getMessageData());
			msgFromServer = new Message(MessageType.GotStudentTDetails, tc);
			break;
			
		
		case GetSubjectNamebyID:
			String subjectName = StudentDBController.getSubjectNamebyID((String)message.getMessageData());
			msgFromServer = new Message(MessageType.GotSubjectNamebyID, subjectName);
			break;
		case PreviewTest:
			Test testPreview = QuestionDBController.getTestPreview((String)message.getMessageData());
			msgFromServer = new Message(MessageType.GotTestPreview, testPreview);
			break;
			
			////////// Shahar ///////
		case RequestExtraTime:
			ArrayList<String> examInfo =
			TestDBController.getExamInfo((String)message.getMessageData());
			
			if(!examInfo.isEmpty()) { 
				boolean canRequestExtraTime =
						TeacherTestDBController.requestExtraTime(examInfo);
				if(canRequestExtraTime) {
			msgFromServer = new Message(MessageType.RequestExtraTime, "Can Request");}
			}
			else {			msgFromServer = new Message
					(MessageType.RequestExtraTime, "The exec code isnt valid");}

			break;
		
		case addRequestForExtraTime:
			TestDBController.addRequestForExtraTime
				((ArrayList<String>)message.getMessageData());		
			break;
		case logOut: //didnt finish
			UserDBController.removeUserFromLoginArr((String)message.getMessageData());
			break;
		case getExtraTime:
			ArrayList<RequestExtraTime> arr = 
			ExtraTimeDBController.getRequest();
			msgFromServer = new Message(MessageType.getExtraTime, arr);
			break;
		/*case GetExamDate:// ezel ragah ze getExamDate
			ArrayList<String> examDate = new ArrayList<>();
			examDate = TestDBController.getExamInfo((String)message.getMessageData());
			msgFromServer = new Message(MessageType.GotExamDate, examDate);
			break;*/
		default:	
			msgFromServer = new Message(MessageType.Error, null);
		}

		try {
			client.sendToClient(msgFromServer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void getTestsCopyByYear(ArrayList<String> arr) {
		ArrayList<testCopy> answer = TeacherTestDBController.getTestCopyByYear(arr);
		msgFromServer = new Message(MessageType.getTestsCopyByYear, answer);
		
	}

	private void getName(String username) {
		String answer = UserDBController.getName(username);
		msgFromServer = new Message(MessageType.Hello, answer);
		System.out.println("after DB: teacher name: " + answer);
	}

	private void getAllTestsDocsBySubject(ArrayList<String> arr) {
		ArrayList<TestDocs> answer = ReportDBController.getAllTestsDocsBySubject(arr);
		msgFromServer = new Message(MessageType.GetAllTestsDocsBySubject, answer);
		
	}

	private void getAllTestsBySubject(String subject) {
		ArrayList<Test> arr = TeacherTestDBController.getAllTestsBySubject(subject);
		msgFromServer = new Message(MessageType.GetAllTestsBySubject, arr);
	}

	private void getNextTID(ArrayList<String> arr) {
		int count = TeacherTestDBController.getNextTID(arr);
		msgFromServer = new Message(MessageType.GetNextTID, count);
		
	}
	
	
	/**get all the tests the teacher wrote
	 * @param username teacher username
	 */
	private void getAllTests(String username) {
		ArrayList<Test> arr = TeacherTestDBController.getAllTests(username);
		msgFromServer = new Message(MessageType.GetAllTests, arr);
		
	}

	/**
	 * @param usersList
	 */
	private void lockTest(ArrayList<String> usersList) {
		Thread[] connections = super.getClientConnections();
		Message msg = new Message(MessageType.LockTest, null);
		for(String s : usersList) {
			for(Thread t : connections) {
				ConnectionToClient c = (ConnectionToClient)t;
				try {
					if(c.getInfo("username").equals(s))
						c.sendToClient(msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
	}

	private void getCourseID(ArrayList<String> arr) {
		String id = TeacherTestDBController.getCourseID(arr);
		if(id == null)
			msgFromServer = new Message(MessageType.Error, "no such course");
		else
			msgFromServer = new Message(MessageType.GetCourseID, id);
		
	}

	private void getQuestionsBySubject(ArrayList<String> arr) {
		ArrayList<Question> answer = TeacherTestDBController.getQuestionsBySubject(arr);
		msgFromServer = new Message(MessageType.GetQuestionsBySubject, answer);
	}

	private void getCoursesBySubject(String subjectID) {
		ArrayList<Course> arr = TeacherTestDBController.getCoursesBySubject(subjectID);
		msgFromServer = new Message(MessageType.getCoursesBySubject, arr);
		
	}

	private void getSubjectID(String bankName) {
		String id = QuestionDBController.getSubjectID(bankName);
		if(id == null)
			msgFromServer = new Message(MessageType.Error, "no such question bank");
		msgFromServer = new Message(MessageType.GetSubjectID, id);
	}

	private void getNextQID(ArrayList<String> arr) {
		int count = QuestionDBController.getNextQID(arr);
		msgFromServer = new Message(MessageType.GetNextQID, count);
		
	}



	@Override
	protected void clientConnected(ConnectionToClient client) {
		super.clientConnected(client);
		clientConnected = "connected";
		clientIp = client.getInetAddress().getHostAddress();
		hostName = client.getInetAddress().getHostName();
		System.out.println(client + " connected !");
	}

	@Override
	protected void serverStarted() {
		super.serverStarted();
		System.out.println("server started and listening on port " + getPort());
	}
}
