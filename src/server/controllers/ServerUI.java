package server.controllers;

import java.io.IOException;

import client.controllers.UserController;
import javafx.application.Application;
import javafx.stage.Stage;
import server.gui.ServerFormController;

public class ServerUI extends Application {
	
	final public static int DEFAULT_PORT = 5555;
	public static ServerFormController serverFormControl;
	public static ServerController serverControl;
	
	public static void main(String[] args) {
	    launch(args);
	  }

	@Override
	public void start(Stage primaryStage) throws Exception {
		serverFormControl = new ServerFormController();
		serverFormControl.start(primaryStage);
		runServer(DEFAULT_PORT);
		
	}
	
	public static void runServer(int p)
	{
	    serverControl = new ServerController(p);
	        try 
	        {
	        	serverControl.listen(); //Start listening for connections
	        } 
	        catch (Exception ex) 
	        {
	          System.out.println("ERROR - Could not listen for clients!");
	          ex.printStackTrace();
	        }
	}
	
	public static String getClientIP() {
		return serverControl.clientIp;
	}
	public static String getHostName() {
		return serverControl.hostName;
	}
	public static String getStatus() {
		return serverControl.clientConnected;
	}
	
	public static void exit() {
		try {
			serverControl.close();
		}catch (IOException e) {
			System.out.println("Error in closing connection");
		}
		System.exit(0);
	}

}
