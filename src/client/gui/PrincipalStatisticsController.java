package client.gui;

import java.util.ArrayList;

import client.controllers.ClientUI;
import client.controllers.TeacherTestController;
import client.controllers.UserController;
import entity.TestDocs;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PrincipalStatisticsController {

    @FXML
    private ComboBox<String> comboFirst;

    @FXML
    private ComboBox<String> comboSecond;

    @FXML
    private TextField txtYear;

    @FXML
    private Button btnLoad;

    @FXML
    private BarChart<?, ?> chartDistribution;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

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
    
    ArrayList<TestDocs> allTestDocs; // all the testsDocs from subject and username
	ArrayList<TestDocs> testDocsSpecific; // all the testsDocs from subject+user filtered by course
	String mode;
	
	private String subject;
	private ObservableList<String> courses;
	private String course;

	private int testDocIndex;

	private String username;

	private int numListeners = 0;

	private ObservableList<String> subjects;
	
    @FXML
    void click_back(ActionEvent event) {

    }

    @FXML
    void click_load(ActionEvent event) {

    }

    @FXML
    void click_next(ActionEvent event) {

    }

    @FXML
    void click_previous(ActionEvent event) {

    }
    
    public void start(String mode) {
    	this.mode = mode;
    	subject = null;
		course = null;
    	initUI();
    }

	private void initUI() {
		allTestDocs = new ArrayList<TestDocs>();
		testDocsSpecific = new ArrayList<TestDocs>();
		testDocIndex = 0;
		
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
		
		if(mode.equals("byTeacher")) {
			comboSecond.setDisable(true);
			comboSecond.setVisible(false);
			comboFirst.setPromptText("Choose Teacher");
			
		}else if(mode.equals("byCourse")) {
			comboFirst.setPromptText("Choose Subject");
			comboSecond.setPromptText("Choose Course");
			subjects = TeacherTestController.getAllSubjects(null);
			comboFirst.getItems().addAll(subjects);
			
		}else if(mode.equals("byStudent")) {
			comboSecond.setDisable(true);
			comboSecond.setVisible(false);
			comboFirst.setPromptText("Choose Student");
		}
		
		if (numListeners == 0) {
			firstListener();
			secondListener();
			numListeners++;
		}
	}

	private void firstListener() {
		comboFirst.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				try {
					if(mode.equals("byCourse")) {
						if(newValue != null) {
							
							subject = newValue;
							comboSecond.getItems().clear();
							courses = TeacherTestController.getCourseList(newValue);
							comboSecond.getItems().addAll(courses);
						}
					}
				}catch(Exception e) {
					ClientUI.display("Error");
				}
				
			}
		});
		
	}
	
	private void secondListener() {
		comboSecond.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				try {
					if(mode.equals("byCourse")) {
						if(newValue != null) {
							course = newValue;
						}
					}
				}catch(Exception e) {
					ClientUI.display("Error");
				}
				
			}
		});
		
	}

}
