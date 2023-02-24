package client.gui;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.controllers.ScreenControllers;
import client.controllers.TeacherTestController;
import client.controllers.UserController;
import entity.Course;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;



/*public class EditCoursesController implements Initializable {

    @FXML
    private Text txtEditCourses;

    @FXML
    private TableView<Course> tbl_Courses;

    @FXML
    private TableColumn<Course, Integer> tbl_bankID;

    @FXML
    private TableColumn<Course, Integer> tbl_CourseID;

    @FXML
    private TableColumn<Course, String> tbl_Name;

    @FXML
    private Label lblBankID;

    @FXML
    private Label lblCourseID;

    @FXML
    private Label lblName;

    @FXML
    private TextField txtBankID;

    @FXML
    private TextField txtCourseID;

    @FXML
    private TextField txtName;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnBack;
    
    
    ObservableList<Course> coursesList;   
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
    
		refreshTable();
			
	}
    
    @FXML
	void click_Back(ActionEvent event)
	{
    	FXMLLoader loader = new FXMLLoader(getClass().getResource
				("TeacherMenuForm.fxml"));
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
    public void click_Add()
    {
    	String bankID = txtBankID.getText();
    	String courseID = txtCourseID.getText();
    	String name = txtName.getText();
    	Course c = new Course(bankID,courseID,name);
    	//TeacherTestController.addCourse(c);
    	refreshTable();
    }
    
    @FXML
    public void click_Delete()
    {
    	String bankID = txtBankID.getText();
    	String courseID = txtCourseID.getText();
    	String name = txtName.getText();
    	Course c = new Course(bankID,courseID,name);
    	TeacherTestController.deleteCourse(c);
    	refreshTable();
    }
    
    @FXML
    public void click_Edit()
    {
    	String bankID =txtBankID.getText();
    	String courseID = txtCourseID.getText();
    	String name = txtName.getText();
    	Course c = new Course(bankID,courseID,name);
    	//TeacherTestController.deleteCourse(c);
    	refreshTable();
    }
   
    public void refreshTable()
    {
    	tbl_Courses.getItems().clear(); // clear table
		tbl_bankID.setCellValueFactory(new PropertyValueFactory<Course,Integer>("bankId"));
		tbl_CourseID.setCellValueFactory(new PropertyValueFactory<Course,Integer>("courseId"));
		tbl_Name.setCellValueFactory(new PropertyValueFactory<Course,String>("name"));
		
		tbl_Courses.setItems(TeacherTestController.refreshCourseTable());
    }
	
    
	
    
    

}*/
