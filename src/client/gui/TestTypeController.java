package client.gui;

import java.io.IOException;
import javafx.event.ActionEvent;
import client.controllers.ClientUI;
import client.controllers.ScreenControllers;
import client.controllers.StudentController;
import client.controllers.UserController;
import entity.Message;
import entity.MessageType;
import entity.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TestTypeController {

	@FXML
	private TextField txtExecCode;

	/**
	 * 
	 * @return execution code.
	 */
	public String getTxtExecCode() {
		return txtExecCode.getText();
	}
    @FXML
    private Button btnOnlineTest;

    @FXML
    private Button btnManualTest;

    @FXML
    private Button btnBack;

    @FXML
    private Label lblIsExecCodeValid;
    
    public static String code;

	
	/**
	 * change the stage to the manual test form in case the execution code is right
	 * 
	 * @param event
	 */
	@FXML
	void clickManualTest(ActionEvent event) {
		UserController.CurrentTestID = null;
		Message msg = new Message(MessageType.execCode, getTxtExecCode());
		ClientUI.accept(msg);
		if (UserController.CurrentTestID != null) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ManualTestForm.fxml"));
			Parent root;
			try {
				ScreenControllers.manualTestController = loader.getController();
				root = loader.load();
				Scene scene = new Scene(root);
				UserController.extraStage = UserController.currentStage; // save the current stage
				UserController.currentStage.setScene(scene);
			} catch (IOException e) {
				e.printStackTrace();
			}
			//UserController.executionCodeForTest = getTxtExecCode(); mi mishtamesh beze?
			code = getTxtExecCode();
			
		}

	}
	
	
	/**
	 * change the stage to the online test form in case the execution code is right
	 * @param event
	 */
	@FXML
    void clickOnlineTest(ActionEvent event) 
    {
    try
    {
    	if(StudentController.isExecutionCodeValid(getTxtExecCode()))
		{
    		code = getTxtExecCode();
    		lblIsExecCodeValid.setText("");
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("OnlineTestForm.fxml"));
    		Parent root;
    		try {
    			ScreenControllers.onlineTestControl = loader.getController();
    			root = loader.load();
    			Scene scene = new Scene(root);
    			Stage onlineTest = new Stage();
    			onlineTest.setScene(scene);
    			UserController.currentStage.hide();
    			UserController.currentStage =onlineTest;
    			onlineTest.show();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		}
    		else
    		{
    			lblIsExecCodeValid.setText("Please Enter A Valid Execution Code.");
    			lblIsExecCodeValid.setTextFill(Color.RED);
        		ClientUI.display("Code Doesnt Exist!");
    		}
    }catch(NullPointerException e)
    {
    	ClientUI.display("Exam Doesnt exist!");
    }
		}
    	


	@FXML
	void clickBack(ActionEvent event) {
		FXMLLoader loader2 = new FXMLLoader(getClass().getResource("StudentMenuForm.fxml")); 																		
		Parent root2;
		try {
			root2 = loader2.load();
			Scene scene = new Scene(root2);
			UserController.currentStage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void start() {
		// TODO Auto-generated method stub
		
	}

}

