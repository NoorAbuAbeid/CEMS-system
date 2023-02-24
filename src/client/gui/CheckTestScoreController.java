package client.gui;

import java.io.IOException;
import java.util.ArrayList;

import client.controllers.ClientUI;
import client.controllers.ScreenControllers;
import client.controllers.TeacherTestController;
import client.controllers.UserController;
import entity.Message;
import entity.MessageType;
import entity.Question;
import entity.testCopy;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CheckTestScoreController {

	@FXML
	private Text txtCheckTestScore;

	@FXML
	private TextField txtYear;

	@FXML
	private Label lblYear;

	@FXML
	private Button btnBack;

	@FXML
	private Button btnShowTestList;

	@FXML
	private Button btnApproveTestScore;

	@FXML
	private Button btnUpdateScore;

	@FXML
	private Button btnSeeTest;

	@FXML
	private TableView<testCopy> tblTestList;

	@FXML
	private TableColumn<testCopy, String> clmTestID;

	@FXML
	private TableColumn<testCopy, String> clmYear;

	@FXML
	private TableColumn<testCopy, String> clmMonth;

	@FXML
	private TableColumn<testCopy, String> clmDay;

	@FXML
	private TableColumn<testCopy, String> clmWriterUsername;

	@FXML
	private TableColumn<testCopy, Integer> clmScore;

	@FXML
	private TableColumn<testCopy, String> clmStudentUsername;

	@FXML
	private TextArea txtReasons;

	@FXML
	private TextField txtNewScore;

	private ObservableList<testCopy> testCopyList;
	private boolean approved;

	@FXML
	void click_approve(ActionEvent event) {
		ArrayList<String> arr = new ArrayList<String>();
		arr.add(tblTestList.getSelectionModel().getSelectedItem().getTestID());
		arr.add(tblTestList.getSelectionModel().getSelectedItem().getYear());
		arr.add(tblTestList.getSelectionModel().getSelectedItem().getMonth());
		arr.add(tblTestList.getSelectionModel().getSelectedItem().getDay());
		arr.add(tblTestList.getSelectionModel().getSelectedItem().getTestWriterUsername());
		arr.add(tblTestList.getSelectionModel().getSelectedItem().getStudentUsername());
		Message msg = new Message(MessageType.ApproveTestCopy, arr);
		ClientUI.accept(msg);

		fillTable(txtYear.getText());
	}

	@FXML
	void click_back(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("TeacherMenuForm.fxml"));
		Parent root;
		try {

			root = loader.load();
			Scene scene = new Scene(root);
			Stage manage = new Stage();
			manage.setScene(scene);
			UserController.currentStage.hide(); // close?
			UserController.currentStage = manage;
			manage.show();
			ScreenControllers.teacherMenuController = loader.getController();
			ScreenControllers.teacherMenuController.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void click_seeTest(ActionEvent event) {

	}

	@FXML
	void click_showTests(ActionEvent event) {
		if (TeacherTestController.validYearTeacherCheckTestScore(txtYear.getText()).equals("valid")) {
			fillTable(txtYear.getText());
			if(tblTestList.getItems().size() == 0)
				ClientUI.display("no unapproved tests");
		} else
			ClientUI.display("Error in year");

	}

	private void fillTable(String year) {
		testCopyList = TeacherTestController.getTestCopyList(year, UserController.username);
		System.out.println("testCopy list: " + testCopyList);
		tblTestList.getItems().clear();
		tblTestList.getItems().addAll(testCopyList);

	}

	@FXML
    void click_update(ActionEvent event) {
		String isValid = TeacherTestController.validNewScoreCheckTestScore(
				txtNewScore.getText(), txtReasons.getText(), 
				tblTestList.getSelectionModel().getSelectedItem());
		if(isValid.equals("valid")) {
			
			ArrayList<String> arr = new ArrayList<String>();
			arr.add(txtNewScore.getText());
			arr.add(txtReasons.getText());
	    	arr.add(tblTestList.getSelectionModel().getSelectedItem().getTestID());
	    	arr.add(tblTestList.getSelectionModel().getSelectedItem().getYear());
	    	arr.add(tblTestList.getSelectionModel().getSelectedItem().getMonth());
	    	arr.add(tblTestList.getSelectionModel().getSelectedItem().getDay());
	    	arr.add(tblTestList.getSelectionModel().getSelectedItem().getTestWriterUsername());
	    	arr.add(tblTestList.getSelectionModel().getSelectedItem().getStudentUsername());
	    	
	    	
			Message msg = new Message(MessageType.UpdateTestCopy, arr);
			ClientUI.accept(msg);
			
			arr.clear();
			arr.add(tblTestList.getSelectionModel().getSelectedItem().getTestID());
	    	arr.add(tblTestList.getSelectionModel().getSelectedItem().getYear());
	    	arr.add(tblTestList.getSelectionModel().getSelectedItem().getMonth());
	    	arr.add(tblTestList.getSelectionModel().getSelectedItem().getDay());
	    	arr.add(tblTestList.getSelectionModel().getSelectedItem().getTestWriterUsername());
	    	arr.add(tblTestList.getSelectionModel().getSelectedItem().getStudentUsername());
			msg = new Message(MessageType.ApproveTestCopy, arr);
			ClientUI.accept(msg);

			fillTable(txtYear.getText());
		}else
			ClientUI.display(isValid);
    }

	public void start() {
		setTableColumns();
	}

	private void setTableColumns() {
		clmTestID.setCellValueFactory(new PropertyValueFactory<testCopy, String>("testID"));
		clmYear.setCellValueFactory(new PropertyValueFactory<testCopy, String>("year"));
		clmMonth.setCellValueFactory(new PropertyValueFactory<testCopy, String>("month"));
		clmDay.setCellValueFactory(new PropertyValueFactory<testCopy, String>("day"));
		clmWriterUsername.setCellValueFactory(new PropertyValueFactory<testCopy, String>("testWriterUsername"));
		clmScore.setCellValueFactory(new PropertyValueFactory<testCopy, Integer>("finalScore"));
		clmStudentUsername.setCellValueFactory(new PropertyValueFactory<testCopy, String>("studentUsername"));

	}

}
