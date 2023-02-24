package entity;

import java.io.Serializable;

public class RequestExtraTime implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String executioncode;
	private String reasons;
	private String time;
	
	public RequestExtraTime(String executioncode, String reasons, String time) {
		super();
		this.executioncode = executioncode;
		this.reasons = reasons;
		this.time = time;
	}
	public String getExecutioncode() {
		return executioncode;
	}
	public void setExecutioncode(String executioncode) {
		this.executioncode = executioncode;
	}
	public String getReasons() {
		return reasons;
	}
	public void setReasons(String reasons) {
		this.reasons = reasons;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

}