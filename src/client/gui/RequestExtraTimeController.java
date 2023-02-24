package client.gui;

import java.io.IOException;
import java.util.ArrayList;

import client.controllers.ClientUI;
import client.controllers.TeacherTestController;
import client.controllers.UserController;
import entity.Message;
import entity.MessageType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

public class RequestExtraTimeController {

    @FXML
    private TextField txtReasons;
    
 
    @FXML
    private TextField txtTime;


    @FXML
    void clickRequest(ActionEvent event) {
    	//execCode,Reasons,Time
    	ArrayList<String> requestDetails = new ArrayList<String>();
    	requestDetails.add
    	(TeacherTestController.executionCodeForExtraTime);
    	requestDetails.add(txtReasons.getText());
    	requestDetails.add(txtTime.getText());
    	Message msg = new Message
    			(MessageType.addRequestForExtraTime,requestDetails);
    	ClientUI.accept(msg);
    	ClientUI.display("Request has been submitted");
    	
    }
    
    
	@FXML
    void clickBack(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageTestForm.fxml"));
		Parent root;
		try {
			root = loader.load();
			Scene scene = new Scene(root);
			UserController.currentStage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}