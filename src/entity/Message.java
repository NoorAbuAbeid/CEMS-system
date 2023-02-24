package entity;

import java.io.BufferedInputStream;
import java.io.Serializable;

/**
 * represents a meesage sent between client and server
 *
 */
public class Message implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	/** the message type - enum*/
	private MessageType msgType;
	
	/** the message data - should be cast to the right object after recieved */
	private Object msgData;
	
	public Message(MessageType messageType, Object messageData) {
	    msgType = messageType;
	    msgData = messageData;
	  }
	  
	  /**
	 * @return returns the type of message - enum
	 */
	public MessageType getMessageType() {
	    return msgType;
	  }
	  
	  /**
	 * @return returns the message data - should be cast to appropriate class
	 */
	public Object getMessageData() {
	    return msgData;
	  }
	  
	  public String toString() {
	    return "MESSAGE";
	  }


}
