package client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

import client.controllers.ClientUI;
import client.controllers.ScreenControllers;
import client.controllers.TeacherTestController;
import client.controllers.UserController;
import entity.Course;
import entity.Message;
import entity.MessageType;

import entity.Question;
import entity.Test;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class CreateTestController {

	@FXML
    private ComboBox<String> cmbBankName;

    @FXML
    private Label CourseNametxt_CreateTest;

    @FXML
    private Label Durationtxt_CreateTest;

    @FXML
    private Label TNotestxt_CreateTest;

    @FXML
    private Label Snotestxt_CreateTest;

    @FXML
    private ComboBox<String> cmbCourseName;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextArea txtStudentNotes;

    @FXML
    private TextArea txtTeacherNotes;

    @FXML
    private Button btnStartEdit;

    @FXML
    private Button btnStopEdit;

    @FXML
    private Label lblTeacherUsername;

    @FXML
    private TextField txtTeacherUsername;

    @FXML
    private Label lblTestID;

    @FXML
    private TextField txtTestID;

    @FXML
    private Button btnAddQuestion;

    @FXML
    private Button btnCreateTest;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnNext;

    @FXML
    private Button btnPrevious;

    @FXML
    private Button btnBack;

	private ObservableList<String> testBankList;
	private ObservableList<String> courseList;
	private ArrayList<Question> testQuestions;
	private ArrayList<Integer> pointsPerQuestion;
	private boolean questionsAdded;
	private Test testForEdit;

	private int subjectListener = 0;
	private int courseListener = 0;
	private boolean fromEdit;
	private ArrayList<Test> allTestList;
	private ArrayList<Test> testInCourse;
	private int testIndex = 0;
	private String mode;
	private boolean startEdit;
	private String subjectID;

	@FXML
	void click_AddQuestion(ActionEvent event) {
		String subject = cmbBankName.getSelectionModel().getSelectedItem();
		String course = cmbCourseName.getSelectionModel().getSelectedItem();
		if (subject != null && course != null) {
			if (mode.equals("regular")) {
				if (questionsAdded == false) {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("AddQuestionForm.fxml"));
					Parent root = null;
					try {
						root = loader.load();
						ScreenControllers.addQuestionControl = loader.getController();
						Scene scene = new Scene(root);
						UserController.currentStage.hide();
						UserController.extraStage.setScene(scene);
						UserController.extraStage.show();
						String username = ScreenControllers.loginFormController.getUsername();
						String subjectID = TeacherTestController.getSubjectID(cmbBankName.getValue());
						ScreenControllers.addQuestionControl.start(subjectID, username, fromEdit, testForEdit, mode);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					UserController.extraStage.show();
					UserController.currentStage.hide();
				}
			} else if (mode.equals("edit")) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("AddQuestionForm.fxml"));
				Parent root = null;
				try {
					root = loader.load();
					ScreenControllers.addQuestionControl = loader.getController();
					Scene scene = new Scene(root);
					UserController.currentStage.hide();
					UserController.extraStage.setScene(scene);
					UserController.extraStage.show();
					String username = ScreenControllers.loginFormController.getUsername();
					String subjectID = TeacherTestController.getSubjectID(cmbBankName.getValue());
					ScreenControllers.addQuestionControl.start(subjectID, username, fromEdit,
							testInCourse.get(testIndex), mode);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else /* viewQuestions - AddQuestions */ {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("AddQuestionForm.fxml"));
				Parent root = null;
				try {
					root = loader.load();
					ScreenControllers.addQuestionControl = loader.getController();
					Scene scene = new Scene(root);
					UserController.currentStage.hide();
					UserController.extraStage.setScene(scene);
					UserController.extraStage.show();
					String username = ScreenControllers.loginFormController.getUsername();
					String subjectID = TeacherTestController.getSubjectID(cmbBankName.getValue());
					ScreenControllers.addQuestionControl.start(subjectID, username, fromEdit,
							testInCourse.get(testIndex), mode);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	@FXML
	void click_Back(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("TeacherMenuForm.fxml"));
		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		UserController.currentStage.setScene(scene);
		ScreenControllers.teacherMenuController.start();
	}

	@FXML
	void click_CreateTest(ActionEvent event) {
		String subject = cmbBankName.getSelectionModel().getSelectedItem();
		String course = cmbCourseName.getSelectionModel().getSelectedItem();

		if (subject == null || course == null) {
			ClientUI.display("choose subject and course first");
			return;
		}
		String subjectID = TeacherTestController.getSubjectID(subject);
		String courseID = TeacherTestController.getCourseID(subjectID, course);

		String teacherUsername = ScreenControllers.loginFormController.getUsername();
		// set test ID
		// get num of tests in specific subject and course
		// int testCount = TeacherTestController.getTCount(subjectID, courseID,
		// teacherUsername);
		// testCount++;
		String tID = TeacherTestController.getNextTestID(subjectID, courseID, teacherUsername);
		System.out.println("try create test: ID: " + tID);
		if (tID.equals("error")) {
			ClientUI.display("maximum number of questions reached");
			return;
		}

		String teacherName = UserController.teacherName;
		String durationString = txtDuration.getText();
		String teacherNotes = txtTeacherNotes.getText();
		String studentNotes = txtStudentNotes.getText();
		Test t = new Test(tID, -1, testQuestions, pointsPerQuestion, teacherName, teacherUsername, teacherNotes,
				studentNotes);
		String valid = TeacherTestController.isValidFieldsCreateTest(t, subject, course, durationString);
		if (valid.equals("valid")) {
			t.setDuration(Integer.parseInt(durationString));
			System.out.println("is valid, sending msg to create");
			Message msg = new Message(MessageType.AddTest, t);
			ClientUI.accept(msg);
			initUI(null);
		} else
			ClientUI.display(valid);

	}

	public void start() {
		mode = "regular";
		initUI(null);
		fromEdit = false;

	}

	public void startEdit(ArrayList<Test> tests) {
		mode = "edit";
		initUI(tests);

	}

	public void startView() {
		mode = "view";
		initUI(null);
	}

	private void bankListener() {

		cmbBankName.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				try {
					subjectID = TeacherTestController.getSubjectID(newValue);
					if (mode.equals("view")) {
						if (newValue != null)
							allTestList = TeacherTestController.getAllTestsBySubject(subjectID);
					} else if (mode.equals("edit")) {
						if (newValue != null) {
							String username = ScreenControllers.loginFormController.getUsername();
							allTestList = TeacherTestController.getAllTests(username);
						}
					}

					cmbCourseName.getItems().clear();
					courseList = TeacherTestController.getCourseList(newValue);
					cmbCourseName.getItems().addAll(courseList);
				} catch (NullPointerException e) {
					return;
				}

			}
		});
	}

	private void courseListener() {
		System.out.println("in courseListener");
		cmbCourseName.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				try {
					testInCourse.clear();
					if (mode.equals("view") || mode.equals("edit")) {
						if (newValue != null) {
							testInCourse.clear();
							for (Test t : allTestList) {
								if (t.getId().substring(2, 4)
										.equals(TeacherTestController.getCourseIDNotDB(subjectID, newValue))) {
									testInCourse.add(t);
								}

							}
						}
						if (testInCourse.size() == 0) {
							ClientUI.display("no tests in course");
							txtDuration.clear();
							txtTeacherNotes.clear();
							txtStudentNotes.clear();
							txtTeacherUsername.clear();
							txtTestID.clear();
						} else {
							testIndex = 0;
							setTestView(0);
						}
					}
				} catch (NullPointerException e) {
					return;
				}

			}

		});
	}

	private void initUI(ArrayList<Test> tests) {
		if (mode.equals("regular")) {
			cmbBankName.getItems().clear();
			cmbCourseName.getItems().clear();
			txtDuration.setText("");
			txtTeacherNotes.setText("");
			txtStudentNotes.setText("");
			//CreateTestTitle.setText("Create Test");

			// add banks to Bank comboBox
			String username = ScreenControllers.loginFormController.getUsername();
			testBankList = TeacherTestController.getAllSubjects(username);
			cmbBankName.getItems().addAll(testBankList);

			testQuestions = new ArrayList<>();
			pointsPerQuestion = new ArrayList<>();
			questionsAdded = false;

			btnEdit.setVisible(false);
			btnEdit.setDisable(true);

			btnStartEdit.setVisible(false);
			btnStartEdit.setDisable(true);

			btnStopEdit.setVisible(false);
			btnStopEdit.setDisable(true);

			btnDelete.setVisible(false);
			btnDelete.setDisable(true);

			btnNext.setVisible(false);
			btnNext.setDisable(true);

			btnPrevious.setVisible(false);
			btnPrevious.setDisable(true);

			lblTeacherUsername.setVisible(true);

			lblTestID.setVisible(false);
			txtTeacherUsername.setVisible(true);
			// name - not username
			txtTeacherUsername.setText(UserController.teacherName);
			txtTeacherUsername.setDisable(true);
			txtTestID.setVisible(false);
			txtTestID.setDisable(true);

		} else if (mode.equals("edit")) {
		//	CreateTestTitle.setText("Edit Test");
			questionsAdded = true;
			String username = ScreenControllers.loginFormController.getUsername();
			testBankList = TeacherTestController.getAllSubjects(username);
			cmbBankName.getItems().addAll(testBankList);
			btnPrevious.setDisable(false);
			btnPrevious.setVisible(true);
			btnNext.setDisable(false);
			btnNext.setVisible(true);
			btnEdit.setDisable(true);
			btnEdit.setVisible(false);
			btnDelete.setDisable(true);
			btnDelete.setVisible(false);
			questionsAdded = true;

			txtDuration.setEditable(false);
			txtStudentNotes.setEditable(false);
			txtTeacherNotes.setEditable(false);

			allTestList = tests;

			testInCourse = new ArrayList<Test>();
			testQuestions = new ArrayList<>();
			pointsPerQuestion = new ArrayList<>();
			btnCreateTest.setDisable(true);
			startEdit = false;

		} else /* mode=view */ {
			// add banks to Bank comboBox
		//	CreateTestTitle.setText("View Test");
			String username = ScreenControllers.loginFormController.getUsername();
			testBankList = TeacherTestController.getAllSubjects(username);
			cmbBankName.getItems().addAll(testBankList);
			questionsAdded = true;
			testQuestions = new ArrayList<>();
			pointsPerQuestion = new ArrayList<>();
			btnPrevious.setDisable(false);
			btnPrevious.setVisible(true);
			btnNext.setDisable(false);
			btnNext.setVisible(true);
			btnEdit.setDisable(true);
			btnEdit.setVisible(false);
			btnDelete.setDisable(true);
			btnDelete.setVisible(false);
			btnStartEdit.setVisible(false);
			btnStartEdit.setDisable(false);
			btnStopEdit.setVisible(false);
			btnStopEdit.setDisable(true);

			txtDuration.setEditable(false);
			txtStudentNotes.setEditable(false);
			txtTeacherNotes.setEditable(false);
			btnCreateTest.setDisable(true);

			allTestList = tests;
			testInCourse = new ArrayList<Test>();
			txtTeacherUsername.setVisible(true);
		}

		if (subjectListener == 0) {
			bankListener();
			subjectListener++;
			System.out.println("bankListener: " + subjectListener);
		}
		if (courseListener == 0) {
			courseListener();
			courseListener++;
			System.out.println("courseListner: " + courseListener);
		}

	}

	public void setTestQuestions(ListView<String> questions) {
		ObservableList<String> arr = questions.getItems();
		for (String s : arr) {
			for (Question q : TeacherTestController.questionsBySubject) {
				if (q.getId().equals(s))
					testQuestions.add(q);
			}
		}
	}

	public void setPointsPerQuestion(ArrayList<Integer> points) {
		pointsPerQuestion = points;
	}

	public void setQuestionsAdded(boolean value) {
		questionsAdded = value;
	}

	@FXML
	void click_delete(ActionEvent event) {
		Test t = testInCourse.get(testIndex);

		Message msg = new Message(MessageType.deleteTest, t);
		ClientUI.accept(msg);
	}

	@FXML
	void click_edit(ActionEvent event) {
		String durationString = txtDuration.getText();
		String teacherNotes = txtTeacherNotes.getText();
		String studentNotes = txtStudentNotes.getText();
		String subject = cmbBankName.getSelectionModel().getSelectedItem();
		String course = cmbCourseName.getSelectionModel().getSelectedItem();

		testInCourse.get(testIndex).setTeacherNotes(teacherNotes);
		testInCourse.get(testIndex).setStudentNotes(studentNotes);

		String valid = TeacherTestController.isValidFieldsCreateTest(testInCourse.get(testIndex), subject, course,
				durationString);
		if (valid.equals("valid")) {
			testInCourse.get(testIndex).setDuration(Integer.parseInt(durationString));

			Message msg = new Message(MessageType.updateTest, testInCourse.get(testIndex));
			ClientUI.accept(msg);
			initUI(null);
		} else
			ClientUI.display(valid);

	}

	@FXML
	void click_next(ActionEvent event) {
		if (testInCourse.size() == 0)
			ClientUI.display("no tests in course");
		else {
			if (testIndex + 1 >= testInCourse.size()) {
				ClientUI.display("no more tests in course");
			} else {
				testIndex++;
				setTestView(testIndex);
			}
		}
	}

	@FXML
	void click_previous(ActionEvent event) {
		if (testInCourse.size() == 0)
			ClientUI.display("no tests in course");
		else if (testIndex == 0) {
			ClientUI.display("no previous test in course");
		} else {
			testIndex--;
			setTestView(testIndex);
		}
	}

	@FXML
	void click_startEdit(ActionEvent event) {
		if (cmbBankName.getValue() != null && cmbCourseName.getValue() != null) {
			cmbBankName.setDisable(true);
			cmbCourseName.setDisable(true);
			btnEdit.setDisable(false);
			btnEdit.setVisible(true);
			startEdit = true;
			btnDelete.setDisable(false);
			btnDelete.setVisible(true);

			txtDuration.setEditable(true);
			txtStudentNotes.setEditable(true);
			txtTeacherNotes.setEditable(true);
		} else
			ClientUI.display("choose subject and course");
	}

	@FXML
	void click_stopEdit(ActionEvent event) {
		cmbBankName.setDisable(false);
		cmbCourseName.setDisable(false);

		txtDuration.setEditable(false);
		txtStudentNotes.setEditable(false);
		txtTeacherNotes.setEditable(false);

		// initUI(null);

		btnEdit.setDisable(true);
		btnEdit.setVisible(false);
		startEdit = false;
	}

	private void setTestView(int i) {
		txtDuration.setText(String.valueOf(testInCourse.get(i).getDuration()));
		txtStudentNotes.setText(testInCourse.get(i).getStudentNotes());
		txtTeacherNotes.setText(testInCourse.get(i).getTeacherNotes());
		txtTeacherUsername.setText(testInCourse.get(i).getTeacherUsername());
		testQuestions = testInCourse.get(i).getQuestions();
		pointsPerQuestion = testInCourse.get(i).getPointsPerQuestion();
		txtTestID.setText(testInCourse.get(i).getId());

	}

}
