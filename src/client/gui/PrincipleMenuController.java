package client.gui;

import java.io.IOException;

import client.controllers.ClientUI;
import client.controllers.UserController;
import entity.Message;
import entity.MessageType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class PrincipleMenuController {

    @FXML
    void clickBack(ActionEvent event) {

    }

    /**
     * 
     * 
     * @param event
     */
    @FXML
    void clickExtraTimeRequests(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("PrincipleRequestForm.fxml"));
		Parent root;
		try {
			root = loader.load();
			Scene scene = new Scene(root);
			UserController.currentStage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

  
    @FXML
    void clickStatisticalReports(ActionEvent event) {
    	System.out.println("yeSSs");
    
    }
    
	@FXML
	void clickLogout(ActionEvent event) {
		Message msg = new Message(MessageType.logOut,UserController.username);
		ClientUI.accept(msg);
		FXMLLoader loader2 = new FXMLLoader(getClass().getResource("LoginForm.fxml"));
		Parent root2;
		try {
			root2 = loader2.load();
			Scene scene = new Scene(root2);
			UserController.currentStage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
   @FXML
    void clickSystemData(ActionEvent event) {
    	System.out.println("yesS");

    }

}