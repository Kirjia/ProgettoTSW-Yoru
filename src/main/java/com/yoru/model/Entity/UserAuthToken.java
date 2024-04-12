package com.yoru.model.Entity;

public class UserAuthToken {
	
	public static final String COLUMN_LABEL1 = "userID";
	public static final String COLUMN_LABEL2 = "selector";
	public static final String COLUMN_LABEL3 = "validator";
	
	private int id;
	private int UserID;
	private String selector;
	private String validator;
	
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
	}
	public String getValidator() {
		return validator;
	}
	public void setValidator(String validator) {
		this.validator = validator;
	}
	public String getSelector() {
		return selector;
	}
	public void setSelector(String selector) {
		this.selector = selector;
	}
	
	
	

}
