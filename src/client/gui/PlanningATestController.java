package client.gui;

import java.io.IOException;
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
import javafx.scene.control.TextField;

public class PlanningATestController {

	/**
	 * test id that the teacher enter
	 */
	public static String TestID;
	/**
	 * teacher username that the teacher enter
	 */
	public static String teacherUsername;
	


	@FXML
	private TextField txtTestID;

	@FXML
	private TextField txtUsername;

	@FXML
	private Button continueWithPlanning;

	@FXML
	private Button btnBack;

	/**
	 * get the test id that the teacher enter
	 * 
	 * @return the test id
	 */
	public String getTxtTestID() {
			TestID =  txtTestID.getText();
		return txtTestID.getText();
	}
	

	/**
	 * get the user name that the teacher enter
	 * 
	 * @return the user name
	 */
	public String getTxtUsername() {
		teacherUsername =  txtUsername.getText();
		return txtUsername.getText();
	}

	@FXML
	void clickContinueWithPlanning(ActionEvent event) {
		ArrayList<String> IDandUsername = new ArrayList<String>();
		IDandUsername.add(getTxtTestID());
		IDandUsername.add(getTxtUsername());
		Message msg = new Message(MessageType.ContinuePlanTest, IDandUsername);
		ClientUI.accept(msg);

		if (UserController.flagForContinuePlanTest) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("PlanningATestSecondStageForm.fxml"));
			Parent root;
			try {
				root = loader.load();
				Scene scene = new Scene(root);
				
				UserController.currentStage.setScene(scene);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else
			ClientUI.display("Test id or username are not vaild");
	}

	@FXML
	void clickBack(ActionEvent event) {
		FXMLLoader loader2 = new FXMLLoader(getClass().getResource("TeacherMenuForm.fxml")); 
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