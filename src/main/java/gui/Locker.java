package gui;

public class Locker {

	private String action;
	
	public void setStartAction(){
		this.action = "START";
	}
	
	public void setStopAction(){
		this.action = "STOP";
	}
	
	public void setSaveAction(){
		this.action = "SAVE";
	}
	
	public String getAction(){
		return this.action;
	}
}
