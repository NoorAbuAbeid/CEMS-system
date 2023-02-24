package client.gui;

import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import client.controllers.ClientUI;
import client.controllers.ScreenControllers;
import client.controllers.StudentController;
import client.controllers.TeacherTestController;
import client.controllers.UserController;
import entity.Course;
import entity.Message;
import entity.MessageType;
import entity.Subject;
import entity.testCopy;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;


import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class PreviewScoresController implements Initializable {

    @FXML
    private TableView<testCopy> tbl_scores;

    @FXML
    private TableColumn<testCopy, String> col_id;

    @FXML
    private TableColumn<testCopy, String> col_subject;
     
    @FXML
    private TableColumn<testCopy, String> col_finalScore;

    @FXML
    private TableColumn<testCopy, String> col_status;

    @FXML
    private Button btnBack;

    @FXML
    private TextField txtYear;

    @FXML
    private Button btnViewTests;

    @FXML
    private Button btnPreview;

    @FXML
    private Label lblYear;
    
    public static ArrayList<testCopy> list;
    public static ArrayList<String> subjectID;	
    public static ArrayList<String> subjectNames; // 0 - subject name for first test
												  // 1 - subject name for second test 
    											  // ....
    public static ObservableList<testCopy> listTestCopy;
    
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		txtYear.setText(null);
		list = StudentController.getAllStudentDetails(UserController.username);
		btnPreview.setVisible(false);
	
		
	}
    
    @FXML
    void previewAnswers()
    {
    	String testId = tbl_scores.getSelectionModel().getSelectedItem().getTestID();
    	int testID = Integer.parseInt(testId);
    	System.out.println(testID);
    	FXMLLoader loader = new FXMLLoader(getClass().getResource
				("TestQuestionsAndAnswersForm.fxml"));
			Parent root = null;
    	
			try {
				root = loader.load();
				ScreenControllers.duringTestControl = loader.getController();
				Scene scene = new Scene(root);

				UserController.currentStage.setScene(scene);
				ScreenControllers.duringTestControl.start(testID);
			} catch (IOException e) {
				e.printStackTrace();
			}
    }
    	
    
    
    
    @FXML
    void viewTest()
    {
    	if(getYear() == null)
    		lblYear.setText("You Must Enter Year First!");
    	else if(checkValidYear(getYear()) == false)
    		lblYear.setText("You did not submit tests in this year!");
    	else
    		refreshTable(getYear());
    	btnPreview.setVisible(true);
    }
    
    public void refreshTable(String year)
    {
    	lblYear.setText("");
    	ArrayList<testCopy> listByYear = new ArrayList<>();
    	for(int i = 0 ; i < list.size(); i++)
    		if(list.get(i).getYear().equals(year))
    			listByYear.add(list.get(i));
    	System.out.println(listByYear);
    	
    	subjectID = new ArrayList<>();
		subjectNames = new ArrayList<>();
		
		for(int i = 0; i < listByYear.size();i++)
			subjectID.add(listByYear.get(i).getTestID().substring(0,2)); //SHOULD BE listByYear.get(i).getTestID().substring(0, 2)
		System.out.println(subjectID);
		
		for(int i = 0; i< subjectID.size();i++)
		{
			subjectNames.add(StudentController.getSubjectNamebyID(subjectID.get(i)));
			listByYear.get(i).setSubject(subjectNames.get(i));
			
		}
		System.out.println(subjectNames);
		System.out.println("list by year : "+listByYear);
	
    	listTestCopy = FXCollections.observableArrayList();
    	for(int i = 0; i < listByYear.size(); i++)
    	{
    		listTestCopy.add(listByYear.get(i));
    		
    	}
    
    	
    	
		tbl_scores.getItems().clear(); // clear table
		col_id.setCellValueFactory(new PropertyValueFactory<testCopy,String>("testID"));
		col_subject.setCellValueFactory(new PropertyValueFactory<testCopy,String>("subject"));
		col_finalScore.setCellValueFactory(new PropertyValueFactory<testCopy,String>("finalScore"));
		col_status.setCellValueFactory(new PropertyValueFactory<testCopy,String>("status"));
		
		tbl_scores.setItems(listTestCopy);
		
		
    }
    
    @FXML
    void clickBack(ActionEvent event) {
    	FXMLLoader loader1 = new FXMLLoader(getClass().getResource
				("StudentMenuForm.fxml"));
		Parent root1;
		try {	
			root1 = loader1.load();
			ScreenControllers.studentMenuControl = loader1.getController();
			Scene scene = new Scene(root1);
			
			UserController.currentStage.setScene(scene);
			ScreenControllers.studentMenuControl.start();
		
		}catch (IOException e) {
			e.printStackTrace();
		}
    	

    }
    
    public boolean checkValidYear(String year)
    {
    	for(int i = 0; i < list.size(); i++)
    	{
    		if(list.get(i).getYear().equals(year))
    			return true;
    	}
    	
    	return false;
    }
    
    public String getYear()
    {
    	return txtYear.getText();
    }


    


	
    
    
    

}//End class
