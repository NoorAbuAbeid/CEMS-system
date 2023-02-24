package client.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.controllers.ClientUI;
import client.controllers.ExtraTimeController;
import entity.Message;
import entity.MessageType;
import entity.RequestExtraTime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PrincipleRequestController implements Initializable {

    @FXML
    private TableView<RequestExtraTime> tblRequests;
    
    @FXML
    private TableColumn<RequestExtraTime, String> col_exec;

    @FXML
    private TableColumn<RequestExtraTime, String> col_reasons;

    @FXML
    private TableColumn<RequestExtraTime, String> col_time;

    @FXML
    private Button btnApprove;

    @FXML
    private Button btnDecline;

    @FXML
    void clickApprove(ActionEvent event) {
    	String execCode = tblRequests.getSelectionModel().
    			getSelectedItem().getExecutioncode();
    	String time = tblRequests.getSelectionModel().
    			getSelectedItem().getTime();
    	ArrayList<String> detailsExtraTime = new ArrayList<String>();
    	//[0]exec[1]time
    	detailsExtraTime.add(execCode);
    	detailsExtraTime.add(time);
    	//System.out.println(execCode);
    	//System.out.println(time);
    	Message  msg = new Message
    			(MessageType.DetailsExtraTime,detailsExtraTime);
    	ClientUI.accept(msg);


    }

    @FXML
    void clickBack(ActionEvent event) {
    	System.out.println("yesS");

    }

    @FXML
    void clickDecline(ActionEvent event) {
    	System.out.println("yesS");
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		col_exec.setCellValueFactory
		(new PropertyValueFactory<RequestExtraTime, String>("executioncode"));
		col_reasons.setCellValueFactory
		(new PropertyValueFactory<RequestExtraTime, String>("reasons"));
		col_time.setCellValueFactory
		(new PropertyValueFactory<RequestExtraTime, String>("time"));
		tblRequests.getItems().clear();
		tblRequests.getItems().addAll(ExtraTimeController.getRequestDetails());		

	}

}