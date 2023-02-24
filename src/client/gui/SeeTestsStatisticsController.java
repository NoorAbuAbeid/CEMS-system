package client.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.controllers.ClientUI;
import client.controllers.ReportController;
import client.controllers.ScreenControllers;
import client.controllers.TeacherTestController;
import client.controllers.UserController;
import entity.Test;
import entity.TestDocs;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TextField;
import server.dbControl.ReportDBController;

public class SeeTestsStatisticsController implements Initializable {

	@FXML
	private ComboBox<String> comboSubject;

	@FXML
	private ComboBox<String> comboCourse;
	
	@FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;
    
    @FXML
	private BarChart<?, ?> chartDistribution;

	@FXML
	private TextField txtYear;

	@FXML
	private Button btnLoad;

	@FXML
	private Button btnNext;

	@FXML
	private Button btnPrevious;

	@FXML
	private Button btnBack;

	@FXML
	private Label lblSemester;

	@FXML
	private Label lblDate;

	@FXML
	private Label lblAssignedTime;

	@FXML
	private Label lblMedian;

	@FXML
	private Label lblAverage;

	@FXML
	private Label lblStudentsNotFinished;

	@FXML
	private Label lblStudentsFinished;

	@FXML
	private Label lblStudentsStarted;

	private String username;
	ArrayList<TestDocs> allTestDocs; // all the testsDocs from subject and username
	ArrayList<TestDocs> testDocsInCourse; // all the testsDocs from subject+user filtered by course
	ObservableList<String> subjects; // list of subject
	ObservableList<String> courses; // list of courses
	private int numListeners = 0; // count of listeners
	private String subjectID;
	private String subject;
	private String course;
	private int testDocIndex;

	@FXML
	void click_back(ActionEvent event) {
		FXMLLoader loader2 = new FXMLLoader(getClass().getResource("TeacherMenuForm.fxml")); 
		Parent root2;
		try {
			root2 = loader2.load();
			Scene scene = new Scene(root2);
			UserController.currentStage.setScene(scene);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ScreenControllers.teacherMenuController.start();
	}

	@FXML
	void click_load(ActionEvent event) {
		String isValid = ReportController.validFieldsSeeTestsStatistics(subject, course, txtYear.getText());

		if (isValid.equals("valid")) {
			btnNext.setDisable(false);
			btnPrevious.setDisable(false);
			subjectID = TeacherTestController.getSubjectID(subject);
			allTestDocs = ReportController.getAllTestsDocsBySubject(subjectID, UserController.username);
			testDocsInCourse.clear();

			// filter all testsDocs for current year
			for (TestDocs t : allTestDocs) {
				String courseID = TeacherTestController.
						getCourseIDNotDB(subjectID, course);
				
				if (t.getId().substring(2, 4).equals(courseID) &&
						t.getYear().equals(txtYear.getText()))
					testDocsInCourse.add(t);
			}
			
			if (testDocsInCourse.size() == 0) {
				ClientUI.display("no tests in course");
				start();
				
			} else {
				testDocIndex = 0;
				setTestDocView(testDocIndex);
			}
		} else {
			ClientUI.display(isValid);
			btnNext.setDisable(true);
			btnPrevious.setDisable(true);
		}
	}

	@FXML
	void click_next(ActionEvent event) {
		System.out.println("testDocs: " + testDocsInCourse);
		if (testDocsInCourse.size() == 0)
			ClientUI.display("no tests in course");
		else {
			if (testDocIndex + 1 >= testDocsInCourse.size()) {
				ClientUI.display("no more tests in course");
			} else {
				testDocIndex++;
				setTestDocView(testDocIndex);
			}
		}
	}

	private void setTestDocView(int testDocIndex) {
		chartDistribution.getData().clear();
		lblSemester.setText(testDocsInCourse.get(testDocIndex).getSemester());
		lblDate.setText(testDocsInCourse.get(testDocIndex).getDate());
		lblAssignedTime.setText(String.valueOf(testDocsInCourse.get(testDocIndex).getAssignedTime()));
		lblStudentsStarted.setText(String.valueOf(testDocsInCourse.get(testDocIndex).getNumStudentsStart()));
		lblStudentsFinished.setText(String.valueOf(testDocsInCourse.get(testDocIndex).getNumStudentsFinishInTime()));
		lblStudentsNotFinished
				.setText(String.valueOf(testDocsInCourse.get(testDocIndex).getNumStudentsNotFinishInTime()));
		lblAverage.setText(String.valueOf(testDocsInCourse.get(testDocIndex).getAverage()));
		lblMedian.setText(String.valueOf(testDocsInCourse.get(testDocIndex).getMedian()));
		
		XYChart.Series series1 = new XYChart.Series<>();
		
		int decile = 1;
		
		for (Integer i : testDocsInCourse.get(testDocIndex).getDistribution()) {
			series1.getData().add(new XYChart.Data(String.valueOf(decile),i));
			decile++;
		}
		chartDistribution.getData().addAll(series1);
	}

	@FXML
	void click_previous(ActionEvent event) {
		if (testDocsInCourse.size() == 0)
			ClientUI.display("no tests in course");
		else if (testDocIndex == 0) {
			ClientUI.display("no previous test in course");
		} else {
			testDocIndex--;
			setTestDocView(testDocIndex);
		}
	}

	public void start() {
		initUI();

		allTestDocs = new ArrayList<TestDocs>();
		testDocsInCourse = new ArrayList<TestDocs>();
		subject = null;
		course = null;
		testDocIndex = 0;

		username = UserController.username;
		subjects = TeacherTestController.getAllSubjects(username);
		comboSubject.getItems().addAll(subjects);

		if (numListeners == 0) {
			subjectListener();
			courseListener();
			numListeners++;
		}
	}

	private void initUI() {
		// comboSubject.getSelectionModel().clearSelection();
		// comboCourse.getSelectionModel().clearSelection();

		lblSemester.setText("");
		lblDate.setText("");
		lblAssignedTime.setText("");
		lblStudentsStarted.setText("");
		lblStudentsFinished.setText("");
		lblStudentsNotFinished.setText("");
		lblAverage.setText("");
		lblMedian.setText("");

		txtYear.setText("");

		btnNext.setDisable(true);
		btnPrevious.setDisable(true);
	}

	private void subjectListener() {
		comboSubject.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				try {
					if (newValue != null) {
						subject = newValue;
						comboCourse.getItems().clear();
						courses = TeacherTestController.getCourseList(newValue);
						comboCourse.getItems().addAll(courses);
					}
				} catch (NullPointerException e) {
					return;
				}

			}
		});
	}

	private void courseListener() {
		comboCourse.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				course = newValue;
			}
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("control initialized !");

	}
	
}
