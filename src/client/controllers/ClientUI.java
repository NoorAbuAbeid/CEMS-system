package client.controllers;

import java.io.IOException;

import javax.swing.JOptionPane;

import client.gui.LoginFormController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ClientUI extends Application {

	public static ClientController clientControl;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		clientControl = new ClientController("localhost", 5555, this);
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/gui/LoginForm.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		ScreenControllers.loginFormController = loader.getController();
		UserController.currentStage = primaryStage;
		UserController.extraStage = new Stage();
		UserController.extraStage2 = new Stage();
		ScreenControllers.loginFormController.start(primaryStage);
		UserController.currentStage.setOnCloseRequest(e -> closeClient());
	}

	private Object closeClient() {
		try {
			clientControl.closeConnection();
			System.out.println("in closeClient");
		} catch (IOException e) {
			System.out.println("Error in closing connection");
		}
		System.exit(0);
		return null;
	}

	public static void exit() {
		try {
			clientControl.closeConnection();
		} catch (IOException e) {
			System.out.println("Error in closing connection");
		}
		System.exit(0);
	}

	public static void display(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}

	public static void accept(Object msg) {
		clientControl.handleMessageFromClientUI(msg);
	}

}
