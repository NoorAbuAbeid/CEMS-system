package client.controllers;

import java.util.ArrayList;

import entity.Message;
import entity.MessageType;
import entity.RequestExtraTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ExtraTimeController {

	public static ArrayList<RequestExtraTime> list;
	
	public static ObservableList<RequestExtraTime> getRequestDetails() {
		Message msg = new Message(MessageType.getExtraTime,null);
		ClientUI.accept(msg);

		return FXCollections.observableArrayList(list);
		
		
	}

	
}