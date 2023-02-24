package client.gui;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import client.controllers.ClientUI;
import client.controllers.ScreenControllers;
import client.controllers.StudentController;
import client.controllers.TeacherTestController;
import client.controllers.UserController;
import entity.Question;
import entity.Test;
import entity.TestBank;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class OnlineTestController {

    @FXML
    private Button btnStartTest;

    @FXML
    private Button btnBack;

    @FXML
    private TextField txtStudentID;

    @FXML
    private Label lblValidStudentID;

    /** A list of all the questions in the specific test.*/
    public static ObservableList<Question> testQuestionsList;
    
    public static Test test;
    
    
    public static ObservableList<Test> tests;
    
    /** List that contains the student details , including his ID,Name,etc...*/
    public static ArrayList<String> studentDetails;
    
    /** List that contains the exam details*/
    public static ArrayList<String> examTime;
    
    /** count how many seconds the student entered after the exam has started.*/
     public static long delayTimeInSecs; 
     
    /** String that contains the exam end hour. */
    public static String endExamHour;
    
    /** String that contains the total duration of an exam.*/
    public static String totalDurationOfExam;
    
    
    @FXML
    void clickBack(ActionEvent event) 
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource
				("TestTypeForm.fxml"));
		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		UserController.currentStage.setScene(scene);
		ScreenControllers.testTypecontrol.start();

    }
    
    /**
     * The clickStart function , first checks if the written student ID exists , if so , 
     * Get the specific exam according to the execution code that has been entered in 
     * The previous screen. Also , calculates the total exam duration and the student
     * total delay time in seconds before he starts an exam.
     * 
     * @param event
     * @throws ParseException
     */
    @FXML
    void clickStart(ActionEvent event) throws ParseException 
    {
    	if(isStudentIDExist())
		{
    		lblValidStudentID.setText("Its A Valid Student ID!");
    		lblValidStudentID.setTextFill(Color.GREEN);
    		test = TeacherTestController.getTestQuestions(getExecutionCode());
    		
    		examTime = new ArrayList<>();
    		examTime = StudentController.getExamDate(getExecutionCode());
    		
       		SimpleDateFormat defaultFormat = new SimpleDateFormat("HH:mm:ss");
    		String examStartHour = examTime.get(1); // exam planned start hour
    		String currentTime = defaultFormat.format(Calendar.getInstance().getTime()); // current time
    		System.out.println("Current time :"+currentTime);
    		String[] newCurrentTime = new String[3];
    		newCurrentTime[0] = currentTime.split(":")[0];
    		newCurrentTime[1] = currentTime.split(":")[1];
    		newCurrentTime[2] = currentTime.split(":")[2];
    		if(currentTime.split(":")[0].equals("00"))
    			newCurrentTime[0] = "24";
    		currentTime = newCurrentTime[0]+":"+newCurrentTime[1]+":"+newCurrentTime[2];
    		System.out.println("Current time after change :"+currentTime);
    		Date startExamHour = defaultFormat.parse(examStartHour);
    		Date currHour = defaultFormat.parse(currentTime);
    		int durationHours = test.getDuration()/60;
    		String durationHrs = "0"+durationHours+"";
   
    		int durationMinutes = test.getDuration() - (60 * durationHours);
    		String durationMns;
    		if(durationMinutes < 10)
    			durationMns = "0"+durationMinutes+"";
    		else
    			durationMns = Integer.toString(durationMinutes);
    		
    		String totalDuration = durationHrs+":"+durationMns+":00";
    		totalDurationOfExam = totalDuration;
    		System.out.println("total duration of the exam is :"+totalDuration);
    	
    		
    		long difference = currHour.getTime() - startExamHour.getTime();
    		
    		long diffSeconds = difference / 1000 % 60;
    		long diffMinutes = difference / (60 * 1000) % 60;
    		long diffHours = difference / (60 * 60 * 1000) % 24;
    		
    		Calendar cal = Calendar.getInstance();
    		cal.setTime(startExamHour);
    		cal.add(Calendar.HOUR_OF_DAY, durationHours);
    		cal.add(Calendar.MINUTE, durationMinutes);
    		startExamHour = cal.getTime();
    		String endHour = defaultFormat.format(startExamHour);
    		endExamHour = endHour;
    		System.out.println("exam ends in :"+endHour+"");
    		
    		
    		
    		
    		delayTimeInSecs = diffHours * 3600 + diffMinutes * 60 + diffSeconds;
    		System.out.println(delayTimeInSecs);
    		
    		if(delayTimeInSecs < 0 )
    		{
    			lblValidStudentID.setText("");
    			ClientUI.display("Exam has not started yet!");
    			return;
    		}
    		else
    		{
    		//get student details to enter to table 'ongoing' in DB.
    		studentDetails = new ArrayList<>();
    		studentDetails.add(0,getExecutionCode());
    		studentDetails.add(1,UserController.username);
    		
    		
    		
    		if(delayTimeInSecs < test.getDuration() * 60)
    		{
    			
    			StudentController.AddStudentToOnGoing(studentDetails);
    			FXMLLoader loader = new FXMLLoader(getClass().getResource
    				("TestQuestionsAndAnswersForm.fxml"));
    			Parent root = null;
        	
    			try {
    				root = loader.load();
    				ScreenControllers.duringTestControl = loader.getController();
    				Scene scene = new Scene(root);

    				UserController.currentStage.setScene(scene);
    				ScreenControllers.duringTestControl.start(0);
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    		else
    		{
    			
    			lblValidStudentID.setText("");
    			ClientUI.display("Exam Has Already Ended!");
    		}
    		}
		}
    	else
    	{
    		lblValidStudentID.setText("Please Enter A Valid Student ID.");
    		lblValidStudentID.setTextFill(Color.RED);
    		ClientUI.display("Student Doesnt Exist!");
    		return;
    	}
}
    /**
     * This function checks if the student entered a valid execution code.
     *  
     * @return True if the execution code valid , otherwise return False.
     */
    public boolean isExecutionCodeValid()
    {
    	if(StudentController.isExecutionCodeValid(getExecutionCode()))
    		return true;
    	return false;

    }
    
    /**
     * Get the execution code that the student entered in the previous screen.
     * @return execution Code.
     */
    public String getExecutionCode()
    {
    	return TestTypeController.code;
    }
    
   
    /**
     * 
     * @return this.test
     */
    public static Test getTest()
    {
    	return test;
    }
    
    /**
     * 
     * @return student ID.
     */
    public String getStudentID()
    {
    	return txtStudentID.getText();
    }
  
    
    /**
     * Check if the current Student ID , exists in the system.
     * @return True if the student ID exists , otherwise return False.
     */
    public boolean isStudentIDExist()
    {
    	if(StudentController.isStudentIDExist(getStudentID()))
    		return true;
    	return false; 
    }
    
 
}
