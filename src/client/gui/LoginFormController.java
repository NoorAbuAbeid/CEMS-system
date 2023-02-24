package client.gui;

import java.io.IOException;

import client.controllers.ClientUI;
import client.controllers.ScreenControllers;
import client.controllers.UserController;
import entity.Message;
import entity.MessageType;
import entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginFormController {

	
	 @FXML
	 private Button btnLogin;

	 @FXML
	 private TextField txtUsername;

	 @FXML
	 private TextField txtPassword;
	 
	 
	public static String username;

	public String getUsername() {
		return txtUsername.getText();
	}

	private String getPassword() {
		return txtPassword.getText();
	}

	public void start(Stage primaryStage) throws IOException {
		
	}

	@FXML
	void Login(ActionEvent event) {

		String logInStatus = UserController.logIn(getUsername(),
				getPassword());
		switch (logInStatus) {
		case "You must fill all the fields":
		case "The user does not exist":
		case "The password is incorrect":
		case "The user is already connected":
			ClientUI.display(logInStatus);
			break;
		case "teacher": // openTeacherMenuForm();

			username = getUsername();

			UserController.username = getUsername();

			FXMLLoader loader = new FXMLLoader(getClass().getResource
					("TeacherMenuForm.fxml"));
			Parent root;
			try {	
				root = loader.load();
				ScreenControllers.teacherMenuController = 
						loader.getController();
				Scene scene = new Scene(root);
				UserController.currentStage.setScene(scene);

				ScreenControllers.teacherMenuController.start();
				Message msg = new Message(MessageType.Hello, username);
				ClientUI.accept(msg);

			//	ScreenControllers.teacherMenuController.start();

			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
			
		case "student": // openStudentMenuForm();

			username = getUsername();
			FXMLLoader loader1 = new FXMLLoader(getClass().getResource
					("StudentMenuForm.fxml"));
			Parent root1;
			try {	
				root1 = loader1.load();
				ScreenControllers.studentMenuControl = loader1.getController();
				Scene scene = new Scene(root1);
				UserController.username = getUsername();
				UserController.currentStage.setScene(scene);
				ScreenControllers.studentMenuControl.start();
				Message msg = new Message(MessageType.Hello, username);
				ClientUI.accept(msg);
			}catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "principle": // openStudentMenuForm();
			UserController.username = getUsername();
			FXMLLoader loader3 = new FXMLLoader(getClass().getResource
					("PrincipleMenuForm.fxml")); // change the name to StudentMenu"Form"
			Parent root3;
			try {	
				root3 = loader3.load();
				Scene scene = new Scene(root3);
				UserController.currentStage.setScene(scene);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		default:
			ClientUI.display("cant read message from server");
		}

	}

}
