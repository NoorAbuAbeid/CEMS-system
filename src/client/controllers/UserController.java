package client.controllers;

import java.io.BufferedInputStream;
import java.sql.Blob;
import java.util.ArrayList;

import entity.Message;
import entity.MessageType;
import entity.User;
import javafx.stage.Stage;

public class UserController {

	/**
	 * true if user exist otherwise false
	 */
	public static boolean userExist;
	/**
	 * true if user connected otherwise false
	 */
	public static boolean flagUserAlreadyConnected;

	/**
	 * true if username and password match otherwise false
	 */
	public static boolean validInfo;

	/**
	 * Permission - the kind of the user {student,principle,teacher}
	 */
	public static String permission;

	/**
	 * the condition of the user after he tried to login
	 */
	public static String logInStatus;

	/**
	 * current stage of the user
	 */
	public static Stage currentStage;
	/**
	 * stage before current stage of the user
	 */
	public static Stage extraStage;

	/**
	 * current id of the test.
	 */
	public static String CurrentTestID;
	

	public static Stage extraStage2;
	

	/**
	 * current byte[] of the manual test.
	 */
	public static byte[] byteManualTest;

	
	/**
	 * flag For Submitted Test Successfully
	 */
	public static boolean flagForSubmittedTestSuccessfully=false;
	/**
	 * flag is true when Username and Test id are matched.
	 */
	public static boolean flagForContinuePlanTest;
	/**
	 * string that indicates the status about inserting the plan test to db
	 */
	public static String InsertPlanTest;
	/**
	 * username of the user 
	 */
	public static String username;
	
	public static String teacherName;
	/**
	 * send to the server the details about the login
	 * 
	 * @param username of the user
	 * @param password of the user
	 * @return the condition of the user - success-teacher,principle,student,or why
	 *         login failed
	 */

	public static String logIn(String username, String password) {
		User currentLogIn = new User(username, password);
		Message msg = new Message(MessageType.logIn, currentLogIn);
		ClientUI.accept(msg);
		return logInStatus;
	}
	
	

	
}
