package client.gui;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

import client.controllers.ClientUI;
import client.controllers.ScreenControllers;
import client.controllers.StudentController;
import client.controllers.UserController;
import entity.Message;
import entity.MessageType;
import entity.testCopy;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class ManualTestController {

	/**
	 * array byte of the uploaded word document file.
	 */
	static byte[] arrByteManualTestUpload;

	/**
	 * flag to know if the user uploaded at least one test
	 */
	static boolean flagForUploadedTest = false;

	public static testCopy tcManual;

	public static ArrayList<String> studentDetails = new ArrayList<String>();

	/**
	 * details for the manual test
	 */
	public static ArrayList<String> manualTestDetails = null;

	@FXML
	private Button btnBack;

	@FXML
	private Button btnDownloadTest;

	@FXML
	private Button btnUploadTest;

	/**
	 * Student chooses where to download the manual test and save it on his
	 * computer.
	 * 
	 * @param event
	 */
	@FXML
	void clickDownloadTest(ActionEvent event) {
		UserController.byteManualTest = null;
		Message msg = new Message(MessageType.downloadManualTest, TestTypeController.code);
		ClientUI.accept(msg);
		if (UserController.byteManualTest != null) {
			System.out.println(UserController.byteManualTest);
			try {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setInitialFileName("Manual Test");
				fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Word document", "*.docx"));
				File selectedDirectory = fileChooser.showSaveDialog(UserController.currentStage);
				if (selectedDirectory == null)
					return; // in case that the user click cancel in choose directory to save the test
				File manualTest = new File(selectedDirectory.getAbsolutePath());

				FileOutputStream fos = new FileOutputStream(manualTest);
				BufferedOutputStream bos = new BufferedOutputStream(fos);

				bos.write(UserController.byteManualTest, 0, UserController.byteManualTest.length);

				bos.flush();
				fos.flush();
				bos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// ADD ongoing
			studentDetails.add(0, TestTypeController.code);
			studentDetails.add(1, UserController.username);
			StudentController.AddStudentToOnGoingManual(studentDetails);

			Message msg2 = new Message(MessageType.getManualTestDetails, TestTypeController.code);
			ClientUI.accept(msg2);

		}
	}

	/**
	 * Student chooses the word document test file he wants to upload
	 * 
	 * @param event
	 */
	@FXML
	void clickUploadTest(ActionEvent event) {
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Word document", "*.docx"));
			File fileToUpload = fileChooser.showOpenDialog(UserController.currentStage);
			if (fileToUpload == null)
				return; // in case that the user click cancel in choose upload file (Manual test)
			arrByteManualTestUpload = new byte[(int) fileToUpload.length()];
			FileInputStream fis = new FileInputStream(fileToUpload);
			BufferedInputStream bis = new BufferedInputStream(fis);
			bis.read(arrByteManualTestUpload, 0, arrByteManualTestUpload.length);
			tcManual = new testCopy();
			tcManual.setArrByteManualTestUpload(arrByteManualTestUpload);

			// System.out.println(arrByteManualTestUpload);
			bis.close();
			flagForUploadedTest = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * If the student wants to change his word document file that he uploaded he
	 * change it in case he already uploaded once. otherwise get a message
	 * perspectively
	 * 
	 * @param event
	 */
	@FXML
	void clickEditTheSubmission(ActionEvent event) {
		if (flagForUploadedTest) {
			clickUploadTest(event);
		} else {
			ClientUI.display("You must to upload a test for edit your submission");
		}
	}

	/**
	 * sends the array byte of the uploaded word document file to the server and
	 * returns to student menu Stage
	 * 
	 * @param event
	 */
	@FXML
	void clickSubmit(ActionEvent event) {

		if (StudentController.flagForLockTest) {
			// goToStudentForm()
			FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentMenuForm.fxml"));
			Parent root;
			try {
				root = loader.load();
				Scene scene = new Scene(root);
				UserController.currentStage.setScene(scene);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}

		if (flagForUploadedTest) {
			tcManual.setTeacherUsername(manualTestDetails.get(3));
			tcManual.setTestWriterUsername(manualTestDetails.get(2));
			tcManual.setTestID(manualTestDetails.get(0));
			tcManual.setYear(manualTestDetails.get(6).split("-")[0]);
			tcManual.setMonth(manualTestDetails.get(6).split("-")[1]);
			tcManual.setDay(manualTestDetails.get(6).split("-")[2]);

			// eich osim ????? tcManual.setActualT_ime();
			tcManual.setStudentUsername(UserController.username); // shahar shina
			tcManual.setArrByteManualTestUpload(arrByteManualTestUpload);

			StudentController.submitTestManual(tcManual);

			if (UserController.flagForSubmittedTestSuccessfully) {

				FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentMenuForm.fxml"));
				Parent root;
				try {
					root = loader.load();
					Scene scene = new Scene(root);
					UserController.currentStage.setScene(scene);
				} catch (IOException e) {
					e.printStackTrace();
				}
				Message msg2 = new Message // olay lasim et 2 ele be ArraList<Object> pnia 1 la DB! basof livdok
				(MessageType.RemoveStudentFromOnGoing, studentDetails);
				ClientUI.accept(msg2);
				// 0id,1duration,2teacherUsername,3execCode,4startHour,5teacherUsernameExec,Date

			}
		} else {
			ClientUI.display("You must upload a test to submit");
		}
	}

	@FXML
	void back(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("TestTypeForm.fxml"));
		Parent root;
		try {
			root = loader.load();
			Scene scene = new Scene(root);
			UserController.currentStage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}