package client.gui;


import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.ArrayList;

import client.controllers.ClientUI;
import client.controllers.ScreenControllers;
import client.controllers.StudentController;
import client.controllers.TeacherTestController;
import client.controllers.UserController;
import entity.Message;
import entity.MessageType;
import entity.Test;
import entity.testCopy;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ManageTestController {


    @FXML
    private TextField txtExecCode;

	public Button getBtnLockTest() {
		return btnLockTest;
	}

	public Button getBtnBack() {
		return btnBack;
	}

	@FXML
    private Button btnLockTest;

    @FXML
    private Button btnBack;

    public String getTxtExecCode() {
		return txtExecCode.getText();
	}

    /**
     *  teacher locks the test
     * @param event
     */
    @FXML 
    void clickLockTest(ActionEvent event) {
    	Message msg = new Message(MessageType.lockTest, getTxtExecCode() );
    	ClientUI.accept(msg);   	
    	System.out.println("AFTER SEND LOCK FROM SERVER");
    }
    
    /**
     * teacher click back and wants to go back to teacher menu
     * @param event
     */
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
    
    /**
     * check if the teacher can request extra time,
     *  when she/he can moves to "request extra time" form
     * @param event
     */
    
    @FXML
    void  clickRequestExtraTime(ActionEvent event) {


    	TeacherTestController.flagForRequestValidExecCode=false;
     	Message msg = new Message
     			(MessageType.RequestExtraTime, getTxtExecCode() );
    	ClientUI.accept(msg);   
    	if(TeacherTestController.flagForRequestValidExecCode) {
    	TeacherTestController.executionCodeForExtraTime =
    			getTxtExecCode();
		FXMLLoader loader2 = new FXMLLoader
				(getClass().getResource("RequestExtraTimeForm.fxml")); 
		Parent root2;
		try {
			root2 = loader2.load();
			Scene scene = new Scene(root2);
			UserController.currentStage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}}
    	else {
    		ClientUI.display("This test is no available");
    	}
    
    }
}