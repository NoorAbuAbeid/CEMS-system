package client.gui;

import java.io.IOException;
import java.util.ArrayList;

import client.controllers.ClientUI;
import client.controllers.ScreenControllers;
import client.controllers.TeacherTestController;
import client.controllers.UserController;
import entity.Question;
import entity.Test;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class AddQuestionController {

	@FXML
	private Text txtHeader;

	@FXML
	private TextField txtQuestionID;

	@FXML
	private TextField txtScore;

	@FXML
	private TextField txtTotalScore;

	@FXML
	private Button btnBack;

	@FXML
	private Button btnView;
	
	@FXML
	private Button btnConfirm;

	@FXML
	private Button btnAdd;

	@FXML
	private Button btnRemove;

	@FXML
	private TableView<Question> tblQuestionList;

	@FXML
	private TableColumn<Question, Integer> col_id;

	@FXML
	private TableColumn<Question, String> col_description;

	// shows the question ID's of the questions that were added to the test
	@FXML
	private ListView<String> lstQuestionsInTest;

	// list of all the questions from same subject
	private ObservableList<String> questionsBySubject;

	private ArrayList<Integer> pointsPerQuestion;

	private int sum;
	private String mode;

	@FXML
	void click_addQuestion(ActionEvent event) {
		if (mode.equals("regular")) {
			int sum = 0;
			for (Integer p : pointsPerQuestion)
				sum += p;
			ObservableList<String> questionsInTest = lstQuestionsInTest.getItems();
			String isValid = TeacherTestController.checkValidQuestionID(txtQuestionID.getText(), txtScore.getText(),
					sum, questionsInTest);
			if (isValid.equals("valid")) {
				lstQuestionsInTest.getItems().add(txtQuestionID.getText());
				pointsPerQuestion.add(Integer.parseInt(txtScore.getText()));
				sum += Integer.parseInt(txtScore.getText());
				txtTotalScore.setText(String.valueOf(sum));
			} else
				ClientUI.display(isValid);
		}
	}

	@FXML
	void click_removeQuestion(ActionEvent event) {
		if (mode.equals("regular")) {
			int index = -1;

			String qID = txtQuestionID.getText();
			if (qID == null || qID.equals("")) {
				ClientUI.display("no id input");
				return;
			}
			ObservableList<String> questionsInTest = lstQuestionsInTest.getItems();
			for (int i = 0; i < questionsInTest.size(); i++) {
				if (questionsInTest.get(i).equals(qID))
					index = i;
			}

			lstQuestionsInTest.getItems().remove(index);
			pointsPerQuestion.remove(index);
			int sum = 0;
			for (Integer i : pointsPerQuestion)
				sum += i;
			txtTotalScore.setText(String.valueOf(sum));
		} else if (mode.equals("edit")) {

		} else {

		}
	}

	@FXML
	void click_viewQuestion(ActionEvent event) {
		Question chosenQuestion = null;
		for (Question q : TeacherTestController.questionsBySubject) {
			if (q.getId().equals(txtQuestionID.getText()))
				chosenQuestion = q;
		}
		if (chosenQuestion == null)
			ClientUI.display("Error in question id");
		else {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateQuestionForm.fxml"));
				Parent root = loader.load();
				ScreenControllers.createQuestionControl = loader.getController();
				Scene scene = new Scene(root);
				UserController.extraStage2.setScene(scene);
				UserController.extraStage2.show();
				ScreenControllers.createQuestionControl.startFromAddQuestion(chosenQuestion);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@FXML
	void click_Back(ActionEvent event) {
		if (mode.equals("regular")) {
			questionsBySubject = null;
			pointsPerQuestion = null;
			ScreenControllers.createTestControl.setQuestionsAdded(false);
			UserController.extraStage.hide();
			UserController.currentStage.show();
		} else {
			questionsBySubject = null;
			pointsPerQuestion = null;
			UserController.extraStage.hide();
			UserController.currentStage.show();
		}
	}

	@FXML
	void click_confirm(ActionEvent event) {
		System.out.println("points: " + pointsPerQuestion);
		int sum = 0;
		for (Integer i : pointsPerQuestion)
			sum += i;
		if (sum == 100) {
			ScreenControllers.createTestControl.setTestQuestions(lstQuestionsInTest);
			ScreenControllers.createTestControl.setPointsPerQuestion(pointsPerQuestion);
			ScreenControllers.createTestControl.setQuestionsAdded(true);
			UserController.extraStage.hide();
			UserController.currentStage.show();
		} else
			ClientUI.display("Total score not 100");
	}

	public void start(String subjectID, String username, boolean fromEdit, Test testForEdit, String mode) {
		col_id.setCellValueFactory(new PropertyValueFactory<Question, Integer>("id"));
		col_description.setCellValueFactory(new PropertyValueFactory<Question, String>("description"));
		sum = 0;
		this.mode = mode;
		fillTable(subjectID, username);
		if (mode.equals("regular")) {
			pointsPerQuestion = new ArrayList<>();
		} else if (mode.equals("edit")) {
			pointsPerQuestion = testForEdit.getPointsPerQuestion();
			for (Integer p : pointsPerQuestion)
				sum += p;
			lstQuestionsInTest.getItems().clear();
			for (int i = 0; i < testForEdit.getQuestions().size(); i++) {
				lstQuestionsInTest.getItems().add(testForEdit.getQuestions().get(i).getId());
			}
			txtTotalScore.setText(String.valueOf(sum));
			txtScore.setEditable(true);
			tblQuestionList.setDisable(false);
			btnAdd.setDisable(false);
			btnRemove.setDisable(false);
		} else /* mode = view */ {
			pointsPerQuestion = testForEdit.getPointsPerQuestion();
			for (Integer p : pointsPerQuestion)
				sum += p;
			lstQuestionsInTest.getItems().clear();
			for (int i = 0; i < testForEdit.getQuestions().size(); i++) {
				lstQuestionsInTest.getItems().add(testForEdit.getQuestions().get(i).getId());
				// sum += testForEdit.getPointsPerQuestion().get(i).getPoints();
			}
			txtTotalScore.setText(String.valueOf(sum));
			txtScore.setEditable(false);
			tblQuestionList.setDisable(true);
			btnAdd.setDisable(true);
			btnRemove.setDisable(true);
			btnConfirm.setDisable(true);		
		}
	}

	private void fillTable(String subjectID, String username) {
		tblQuestionList.getItems().clear();
		tblQuestionList.getItems().setAll(TeacherTestController.getQuestionsBySubject(subjectID, username));

	}

}
