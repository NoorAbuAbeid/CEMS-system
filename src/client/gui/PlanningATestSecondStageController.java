package client.gui;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import client.controllers.ClientUI;
import client.controllers.ScreenControllers;
import client.controllers.UserController;
import entity.Message;
import entity.MessageType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class PlanningATestSecondStageController {

	/*public static void main(String[] args) {

		String a = "11:22:00";
		SimpleDateFormat sdf = new SimpleDateFormat("H:m:s");
		sdf.setLenient(false);

		try {
			java.util.Date d = sdf.parse(a);
			
			System.out.println(sdf.format(d));
			String a2 = sdf.format(d);
			String[] c = a2.split(":");
			System.out.println(c[0]);
			System.out.println(c[1]);
			System.out.println(c[2]);
			System.out.println( Integer.parseInt(c[0])));


		} catch (ParseException e) {

			e.printStackTrace();
		}
	}*/
	
	/**
	 * teacher that wants to execute the test
	 */
	public static String teacherUsernameExecuteTest;
	
	public static String getTeacherUsernameExecuteTest() {
		 return teacherUsernameExecuteTest= UserController.username;
	}

	@FXML
	private DatePicker txtDate;

	@FXML
	private TextField txtExecutionCode;

	@FXML
	private TextField txtStartTime;

	@FXML
	private Button btnBuildTest;

	@FXML
	private Button btnBack;



	public String getTxtDate() {
		return txtDate.getEditor().getText();
	}

	public String getTxtExecutionCode() {
		return txtExecutionCode.getText();
	}

	public String getTxtStartTime() {
		return txtStartTime.getText();
	}


	@FXML
	void clickBuildTest(ActionEvent event) {
		UserController.InsertPlanTest = null;
		ArrayList<String> planTest = new ArrayList<String>();
		planTest.add(getTxtExecutionCode());
		planTest.add(getTxtStartTime());
		planTest.add(getTeacherUsernameExecuteTest());
		planTest.add(getTxtDate());

		Message msg = new Message(MessageType.InsertPlanTest, planTest);
		ClientUI.accept(msg);
		switch (UserController.InsertPlanTest) {

		case "Execution code must be 4 fields, digits and letters":
		case "Execuion Code already exist. please choose another one":
		case "Start time must be in format hh:mm:ss":
		case "Duration must be a number between 0-480":
			// case VALID DATE!!!!!zarih laosif bdika
			ClientUI.display(UserController.InsertPlanTest);
			break;
		case "ok": // insert to cems.test executioncode, 	
			ClientUI.display("Successfully created");
			ArrayList<String> idUsernameExec = new ArrayList<String>();
			idUsernameExec.add(PlanningATestController.TestID);
			idUsernameExec.add(PlanningATestController.teacherUsername);
			idUsernameExec.add(getTxtExecutionCode());
			Message msg2 = new Message
					(MessageType.AddExecCodeToTestDB,idUsernameExec);
			ClientUI.accept(msg2);
			
			break;
		}

	}

	@FXML

	/**
	 * 
	 * @param event
	 */
	void clickBack(ActionEvent event) {
		FXMLLoader loader2 = new FXMLLoader(getClass().getResource("PlanningATestForm.fxml"));
		Parent root2;
		try {
			root2 = loader2.load();
			Scene scene = new Scene(root2);
			UserController.currentStage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}