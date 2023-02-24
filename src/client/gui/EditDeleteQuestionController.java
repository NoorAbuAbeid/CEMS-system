package client.gui;

import java.io.IOException;

import client.controllers.ScreenControllers;
import client.controllers.TeacherTestController;
import client.controllers.UserController;
import entity.Question;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class EditDeleteQuestionController {

	@FXML
	private ComboBox<String> cmbSubject;

	@FXML
	private ListView<String> lstQuestions;

	@FXML
	private TextArea txtDescription;

	@FXML
	private Button btnBack;

	@FXML
	private Button btnEdit;

	private ObservableList<String> questions = FXCollections.observableArrayList();
	private int countListeners = 0;

	@FXML
	void click_back(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("TeacherMenuForm.fxml"));
		Parent root;
		try {

			root = loader.load();
			ScreenControllers.teacherMenuController = loader.getController();
			Scene scene = new Scene(root);
			UserController.currentStage.setScene(scene);
			ScreenControllers.teacherMenuController.start();
			;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void click_edit(ActionEvent event) {
		if (lstQuestions.getSelectionModel().getSelectedItem() != null) {

			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateQuestionForm.fxml"));
				Parent root = loader.load();
				ScreenControllers.createQuestionControl = loader.getController();
				Scene scene = new Scene(root);
				UserController.currentStage.setScene(scene);
				String id = lstQuestions.getSelectionModel().getSelectedItem();
				String username = ScreenControllers.loginFormController.getUsername();
				Question q = TeacherTestController.getQuestionByID(id, username);
				ScreenControllers.createQuestionControl.setQuestionForEdit(q);
				ScreenControllers.createQuestionControl.setFromEdit(true);
				ScreenControllers.createQuestionControl.startEdit();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void start() {
		initUI();
		// bankListener();
		// setDescription();
		txtDescription.setEditable(false);
	}

	private void initUI() {
		String username = ScreenControllers.loginFormController.getUsername();
		cmbSubject.getItems().clear();
		cmbSubject.getItems().addAll((TeacherTestController.getAllSubjects(username)));
		System.out.println(TeacherTestController.getAllSubjects(username));
		if (countListeners == 0) {
			bankListener();
			setDescription();
			countListeners++;
		}

	}

	private void setDescription() {
		lstQuestions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				for (Question q : TeacherTestController.questionsBySubject) {
					if (q.getId().equals(newValue)) {
						txtDescription.setText(q.getDescription());
						return;
					}
				}

			}
		});

	}

	private void bankListener() {

		cmbSubject.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				System.out.println("start change:lst= " + lstQuestions.getItems());
				lstQuestions.getItems().clear();
				System.out.println("lst after clear: " + lstQuestions.getItems());
				System.out.println("questions: " + questions);
				try {
					if (newValue != null || !newValue.equals("")) {
						String username = ScreenControllers.loginFormController.getUsername();
						String subject = cmbSubject.getSelectionModel().getSelectedItem();
						String subjectID = TeacherTestController.getSubjectID(subject);

						TeacherTestController.getQuestionsBySubject(subjectID, username);
						for (Question q : TeacherTestController.questionsBySubject)
							lstQuestions.getItems().add(q.getId());
						System.out.println("questions after add: " + questions);
					}
				} catch (NullPointerException e) {
					return;
				}

			}
		});
	}

}
